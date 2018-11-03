package com.pinyougou.search.service.impl;

import java.util.Arrays;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinyougou.search.service.ItemSearchService;

@Component
public class itemDeleteListener implements MessageListener{

	@Autowired
	private ItemSearchService itemSearchService;
	
	@Override
	public void onMessage(Message message) {
		ObjectMessage text = (ObjectMessage)message;
		try {
			Long[] ids = (Long[]) text.getObject();
			System.out.println("itemSearchService监听到消息："+ids);
			itemSearchService.deleteByGoodsIds(Arrays.asList(ids));
			System.out.println("成功删除索引库的记录");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
