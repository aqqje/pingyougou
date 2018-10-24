package com.aqqje.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dome.pojo.TbItem;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-solr.xml")
public class TestTemplate {

	@Autowired
	private SolrTemplate solrTemplate;
	
	/**
	 * 增加（修改）
	 */
	@Test
	public void test1() {
		TbItem item = new TbItem();
		item.setId(1L);
		item.setBrand("华为");
		item.setCategory("手机");
		item.setGoodsId(1L);
		item.setSeller("华为2号专卖店");
		item.setTitle("华为Mate9");
		item.setPrice(new BigDecimal(2000));	
		solrTemplate.saveBean(item);//增加与修改
		solrTemplate.commit();// 提交
	}
	
	/**
	 * 按主键查询
	 */
	@Test
	public void test2() {
		TbItem item = solrTemplate.getById(1, TbItem.class);
		System.out.println(item.getTitle());
	}
	/**
	 * 按主键删除
	 */
	@Test
	public void test3() {
		solrTemplate.deleteById("1");
		solrTemplate.commit();//提交 
	}
	
	/**
	 * 分页查询--插入100条记录
	 */
	@Test
	public void test4() {
		//插入100条记录
		List<TbItem> list = new ArrayList<>();
		for(int i=1; i<=100; i++) {
			TbItem item = new TbItem();
			item.setId(i+1L);
			item.setBrand("华为");
			item.setCategory("手机");
			item.setGoodsId(1L);
			item.setSeller("华为2号专卖店");
			item.setTitle("华为Mate9"+i);
			item.setPrice(new BigDecimal(2000+i));	
			list.add(item);
		}
		solrTemplate.saveBeans(list);
		solrTemplate.commit();
	}
	/**
	 * 分页查询--分页
	 */
	@Test
	public void test5() {
		SimpleQuery query = new SimpleQuery("*:*");
		Criteria criteria = new Criteria("item_title").contains("2");
		criteria.and("item_title").contains("6");
		query.addCriteria(criteria );
		query.setOffset(10);//开始索引默认(0)
		query.setRows(10);//每页记录数默认(10)
		ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
		System.out.println("总记录数"+page.getTotalElements());
		List<TbItem> list = page.getContent();
		showList(list);
	}
	/**
	 * 遍历list集合
	 * @param list
	 */
	private void showList(List<TbItem> list) {
		for(TbItem item: list) {
			System.out.println(item.getTitle()+"------>"+item.getPrice());
		}
	}
	/**
	 * 删除所有数据
	 */
	@Test
	public void deleAll() {
		SolrDataQuery query= new SimpleQuery("*:*");
		solrTemplate.delete(query);
		solrTemplate.commit();
	}
}
