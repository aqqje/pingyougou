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
	 * ���ӣ��޸ģ�
	 */
	@Test
	public void test1() {
		TbItem item = new TbItem();
		item.setId(1L);
		item.setBrand("��Ϊ");
		item.setCategory("�ֻ�");
		item.setGoodsId(1L);
		item.setSeller("��Ϊ2��ר����");
		item.setTitle("��ΪMate9");
		item.setPrice(new BigDecimal(2000));	
		solrTemplate.saveBean(item);//�������޸�
		solrTemplate.commit();// �ύ
	}
	
	/**
	 * ��������ѯ
	 */
	@Test
	public void test2() {
		TbItem item = solrTemplate.getById(1, TbItem.class);
		System.out.println(item.getTitle());
	}
	/**
	 * ������ɾ��
	 */
	@Test
	public void test3() {
		solrTemplate.deleteById("1");
		solrTemplate.commit();//�ύ 
	}
	
	/**
	 * ��ҳ��ѯ--����100����¼
	 */
	@Test
	public void test4() {
		//����100����¼
		List<TbItem> list = new ArrayList<>();
		for(int i=1; i<=100; i++) {
			TbItem item = new TbItem();
			item.setId(i+1L);
			item.setBrand("��Ϊ");
			item.setCategory("�ֻ�");
			item.setGoodsId(1L);
			item.setSeller("��Ϊ2��ר����");
			item.setTitle("��ΪMate9"+i);
			item.setPrice(new BigDecimal(2000+i));	
			list.add(item);
		}
		solrTemplate.saveBeans(list);
		solrTemplate.commit();
	}
	/**
	 * ��ҳ��ѯ--��ҳ
	 */
	@Test
	public void test5() {
		SimpleQuery query = new SimpleQuery("*:*");
		Criteria criteria = new Criteria("item_title").contains("2");
		criteria.and("item_title").contains("6");
		query.addCriteria(criteria );
		query.setOffset(10);//��ʼ����Ĭ��(0)
		query.setRows(10);//ÿҳ��¼��Ĭ��(10)
		ScoredPage<TbItem> page = solrTemplate.queryForPage(query, TbItem.class);
		System.out.println("�ܼ�¼��"+page.getTotalElements());
		List<TbItem> list = page.getContent();
		showList(list);
	}
	/**
	 * ����list����
	 * @param list
	 */
	private void showList(List<TbItem> list) {
		for(TbItem item: list) {
			System.out.println(item.getTitle()+"------>"+item.getPrice());
		}
	}
	/**
	 * ɾ����������
	 */
	@Test
	public void deleAll() {
		SolrDataQuery query= new SimpleQuery("*:*");
		solrTemplate.delete(query);
		solrTemplate.commit();
	}
}
