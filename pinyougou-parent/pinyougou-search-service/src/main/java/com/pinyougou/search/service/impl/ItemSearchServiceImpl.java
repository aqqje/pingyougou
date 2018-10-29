package com.pinyougou.search.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.FilterQuery;
import org.springframework.data.solr.core.query.GroupOptions;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleFilterQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.GroupEntry;
import org.springframework.data.solr.core.query.result.GroupPage;
import org.springframework.data.solr.core.query.result.GroupResult;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;

@Service(timeout=5000)
public class ItemSearchServiceImpl implements ItemSearchService{

	@Autowired
	private SolrTemplate solrTemplate;
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	public Map<String, Object> search(Map searchMap) {
		Map<String, Object> map=new HashMap<>();
		//0.关键字空格处理
		String keywords = (String)searchMap.get("keywords");
		searchMap.put("keywords",keywords.replace(" ", ""));
		//1.根据关键字搜索(高亮显示)
		map.putAll(searchList(searchMap));
		//2.根据关键字分级查询搜索商品分类
		List<String> categoryList = searchCategoryList(searchMap);
		map.put("categoryList", categoryList);
		//3.获取品牌和模板列表
		if(categoryList.size()>0) {
			map.putAll(searchBrandAndSpecList(categoryList.get(0)));
		}
		return map;
	}
	/**
	 * 根据关键字搜索(高亮显示)
	 * @param searchMap
	 * @return
	 */
	private Map<String,Object> searchList(Map searchMap) {
		Map<String, Object> map=new HashMap<>();
		HighlightQuery query = new SimpleHighlightQuery();
		//1.1设置高亮选项
		HighlightOptions highlightOptions = new HighlightOptions().addField("item_title");//设置高亮域
		highlightOptions.setSimplePrefix("<em style='color:red'>");//高亮前缀
		highlightOptions.setSimplePostfix("</em>");//高亮后缀
		query.setHighlightOptions(highlightOptions);
		//1.2搜索关键字/条件
		Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(criteria);
		//2.1商品分类过滤条件
		if(!"".equals(searchMap.get("category"))) {
			Criteria filterCriteria = new Criteria("item_category").is(searchMap.get("category"));
			FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
			query.addFilterQuery(filterQuery);
		}
		//2.2商品品牌过滤条件
		if(!"".equals(searchMap.get("brand"))) {
			Criteria filterCriteria = new Criteria("item_brand").is(searchMap.get("brand"));
			FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
			query.addFilterQuery(filterQuery );
		}
		//2.3商品规格过滤条件
		if(searchMap.get("spec")!=null) {
			Map<String,String> specMap = (Map)searchMap.get("spec");
			for(String key:specMap.keySet()) {
				Criteria filterCriteria = new Criteria("item_spec_"+key).is(specMap.get(key));
				FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria );
				query.addFilterQuery(filterQuery );
			}
		}
		
		//2.4商品价格过滤条件
		if(!"".equals(searchMap.get("price"))) {
			String[] price = ((String)searchMap.get("price")).split("-");
			if(!price[0].equals("0")) {//如果起始价格不是等于0
				Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(price[0]);
				FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
				query.addFilterQuery(filterQuery);
			}
			if(!price[1].equals("*")) {//如果区间起点不等于0
				Criteria filterCriteria = new Criteria("item_price").lessThanEqual(price[1]);
				FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
				query.addFilterQuery(filterQuery);
			}
		}
		//3.1分页查询
		int pageNo = (Integer)searchMap.get("pageNo");//提取页码
		if(pageNo==0) {
			pageNo=1;//默认
		}
		int pageSize = (Integer)searchMap.get("pageSize");//提取页实体数量
		if(pageSize==0) {
			pageSize=40;//默认
		}
		query.setOffset((pageNo-1)*pageSize);//offset第一条记录索引
		query.setRows(pageSize);
		//4.1高亮
		HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query , TbItem.class);
		for(HighlightEntry<TbItem> entry: page.getHighlighted()) {//循环高亮实体的入口
			TbItem entity = entry.getEntity();//获取原实体类
			if(entry.getHighlights().size()>0 && entry.getHighlights().get(0).getSnipplets().size()>0) {
				entity.setTitle(entry.getHighlights().get(0).getSnipplets().get(0));//设置高亮结果
			}
		}
		map.put("rows", page.getContent());
		map.put("totalPages", page.getTotalPages());//总页数
		map.put("total",page.getTotalElements());//总记录数
		return map;

	}
	/**
	 * 根据关键字分级查询搜索商品分类
	 */
	private List searchCategoryList(Map searchMap) {
		List<String> categoryList = new ArrayList<>();
		
		Query query = new SimpleQuery("*:*");
		//搜索关键字/条件
		Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords")); // where ...
		query.addCriteria(criteria);
		
		//分组选项
		GroupOptions groupOptions = new GroupOptions().addGroupByField("item_category");//添加分组域
		//设置分级选项
		query.setGroupOptions(groupOptions);// groud by ...
		//分组查询
		GroupPage<TbItem> page = solrTemplate.queryForGroupPage(query, TbItem.class);
		//获取分组结果集
		GroupResult<TbItem> groupResult = page.getGroupResult("item_category");
		//得到分组结果入口页
		Page<GroupEntry<TbItem>> entries = groupResult.getGroupEntries();
		//分组入口集合
		List<GroupEntry<TbItem>> content = entries.getContent();
		for(GroupEntry<TbItem> entry:content) {
			//将分组结果添加到商品分类集合中
			categoryList.add(entry.getGroupValue());
		}
		return categoryList;
	}	
	
	
	private Map searchBrandAndSpecList(String category){
		Map map = new HashMap<>();
		//根据分类名获得分类ID
		Long typeId = (Long)redisTemplate.boundHashOps("itemCat").get(category);
		if(typeId != null) {
			//获取品牌列表
			List brandList =  (List)redisTemplate.boundHashOps("brandList").get(typeId);
			//获取模板列表
			List specList = (List)redisTemplate.boundHashOps("specList").get(typeId);
			map.put("brandList", brandList);
			map.put("specList", specList);
		}
		return map;
		
	}
}
