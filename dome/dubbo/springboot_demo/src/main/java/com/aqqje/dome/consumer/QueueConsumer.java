package com.aqqje.dome.consumer;

import java.util.Map;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {

	@JmsListener(destination="aqqje_map")
	public void readMessage(Map map) {
		System.out.println("接收到消息："+map);
	}
}
