package com.pinyougou.soltutil;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrDataQuery;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import com.pinyougou.pojo.TbItemExample.Criteria;

@Component
public class SolrUtil {

	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private SolrTemplate solrTemplate;

	/**
	 * 导入商品到 solr 索引库
	 */
	void importItemData(){
		TbItemExample example=new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andStatusEqualTo("1");//已审核
		List<TbItem> itemList = itemMapper.selectByExample(example);
		System.out.println("===商品列表===");
		for(TbItem item:itemList){
			System.out.println(item.getTitle());	
			Map spcMap = JSON.parseObject(item.getSpec(), Map.class);//将spec字段中的json字符串转换为map
			item.setSpcMap(spcMap);
		}		
		solrTemplate.saveBeans(itemList);//给带注解的字段赋值
		solrTemplate.commit();
		System.out.println("===结束===");			
	}	
	/**
	 * 从 solr 索引库删除所有数据
	 */
	void deleteAll() {
		SolrDataQuery query = new SimpleQuery("*:*");
		solrTemplate.delete(query);
		solrTemplate.commit();
		System.out.println("删除成功");
	}
	
	/**/
	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("classpath*:spring/applicationContext*.xml");
		SolrUtil solrUtil=  (SolrUtil) context.getBean("solrUtil");
		solrUtil.deleteAll();
	}
}
