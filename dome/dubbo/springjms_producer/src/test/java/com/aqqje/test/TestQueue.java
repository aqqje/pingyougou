package com.aqqje.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.aqqje.dome.QueueProducer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-jms-producer.xml")
public class TestQueue {
	
	@Autowired
	private QueueProducer qeueProducer;
	
	@Test
	public void testSent() {
		qeueProducer.setTextMessage("spring-jms-点对点");
	}
}
