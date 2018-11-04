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
		Map<String,String> map=new HashMap<>();
		map.put("phoneNumbers", "13548522461");
		map.put("signName", "品优购");		
		map.put("templateCode", "SMS_150182480");		
		map.put("templateParam", "{\"name\":\"欧明\"}");		
		jmsMessagingTemplate.convertAndSend("sms",map);
	}
}
