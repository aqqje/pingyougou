package com.pinyougou.search.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;

@Service(timeout=5000)
public class ItemSearchServiceImpl implements ItemSearchService{

	@Autowired
	private SolrTemplate solrTemplate;
	
	@Override
	public Map<String, Object> search(Map searchMap) {
		Map<String, Object> map=new HashMap<>();
		map.putAll(searchList(searchMap));
		return map;
	}
	/**
	 * 根据关键字搜索
	 * @param searchMap
	 * @return
	 */
	private Map<String,Object> searchList(Map searchMap) {
		Map<String, Object> map=new HashMap<>();
		HighlightQuery query = new SimpleHighlightQuery();
		//设置高亮选项
		HighlightOptions highlightOptions = new HighlightOptions().addField("item_title");//设置高亮域
		highlightOptions.setSimplePrefix("<em style='color:red'>");//高亮前缀
		highlightOptions.setSimplePostfix("</em>");//高亮后缀
		query.setHighlightOptions(highlightOptions);
		//搜索关键字/条件
		Criteria criteria=new Criteria("item_keywords").is(searchMap.get("keywords"));
		query.addCriteria(criteria);
		// 高亮
		HighlightPage<TbItem> page = solrTemplate.queryForHighlightPage(query , TbItem.class);
		for(HighlightEntry<TbItem> entry: page.getHighlighted()) {//循环高亮实体的入口
			TbItem entity = entry.getEntity();//获取原实体类
			if(entry.getHighlights().size()>0 && entry.getHighlights().get(0).getSnipplets().size()>0) {
				entity.setTitle(entry.getHighlights().get(0).getSnipplets().get(0));//设置高亮结果
			}
		}
		map.put("rows", page.getContent());
		return map;

	}
}
