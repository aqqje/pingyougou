package com.pinyougou.page.service.impl;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinyougou.page.service.ItemPageService;

@Component
public class pageDeleteListener implements MessageListener{

	@Autowired
	private ItemPageService itemPageService;
	
	@Override
	public void onMessage(Message message) {
		ObjectMessage text = (ObjectMessage)message;
		try {
			Long[] ids =(Long[])text.getObject();
			System.out.println("itemPageService接收消息："+ids);
			for(Long id: ids) {
				boolean flag = itemPageService.deleteById(id);
				System.out.println(id+"--状态--"+flag);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
