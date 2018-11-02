package com.aqqje.dome;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * ActiveMQ 消费者（点对点）
 * @author aqqje
 *
 */
public class QueueConsumer {
	public static void main(String[] args) throws JMSException, IOException {
		//1.创建连接工厂
		ConnectionFactory ConnectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.128:61616");
		//2.获取连接
		Connection connection = ConnectionFactory.createConnection();
		//3.启动连接
		connection.start();
		//4.获取session对象
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		//5.创建队列对象
		Queue queue = session.createQueue("test-queue");
		//6.创建消费者
		MessageConsumer consumer = session.createConsumer(queue);
		//7.监听消息
		consumer.setMessageListener(new MessageListener() {

			@Override
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage)message;
				try {
					System.out.println("接收到消息："+textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
			
		});
		//8.等待键盘输入
		System.in.read();
		//关闭资源
		consumer.close();
		session.close();
		connection.close();
	}
}
