package com.aqqje.dome.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QueueMQcontroller {

	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;
	
	@RequestMapping("/send")
	public void send() {
		Map map=new HashMap<>();
		map.put("mobile", "13900001111");
		map.put("content", "恭喜获得10元代金券");		
		jmsMessagingTemplate.convertAndSend("aqqje_map",map);
	}
}
