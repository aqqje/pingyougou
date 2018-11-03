package com.aqqje.dome;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

/**
 * spring 整合 jms
 * @author aqqje
 *
 */
@Component
public class TopicPordcer {

	@Autowired
	private JmsTemplate jmsTemplate; 
	
	@Autowired
	private ActiveMQTopic topicTextDestination;
	
	public void sendMessage(String text) {
		jmsTemplate.send(topicTextDestination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(text);
			}
			
		});
	}
}
