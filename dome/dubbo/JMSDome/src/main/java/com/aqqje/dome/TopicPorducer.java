package com.aqqje.dome;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * ActiveMQ 生产者（发布）
 * @author aqqje
 *
 */
public class TopicPorducer {
	public static void main(String[] args) throws JMSException {
		//1.创建连接工厂
		ConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		//2.获取连接
		Connection connection = activeMQConnectionFactory.createConnection();
		//3.启动连接
		connection.start();
		//4.获取session
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.创建主题对象
		Topic topic = session.createTopic("test-topic");
		//6.创建消息生产者
		MessageProducer producer = session.createProducer(topic);
		//7.创建消息
		TextMessage message = session.createTextMessage("欢迎来到ActiveMQ世界！");
		//8.发送消息
		producer.send(message);
		//9.关闭资源
		producer.close();
		session.close();
		connection.close();
	}
}
