package com.pinyougou.page.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinyougou.page.service.ItemPageService;

@Component
public class pageGenListener implements MessageListener{

	@Autowired
	private ItemPageService itemPageService;
	
	@Override
	public void onMessage(Message message) {
		TextMessage text = (TextMessage)message;
		try {
			String id = text.getText();
			System.out.println("itemPageService接收到消息："+id);
			itemPageService.genItemPage(Long.parseLong(id));
			System.out.println(id+".html生成");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
