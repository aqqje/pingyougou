package com.aqqje.sms.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

@Component
public class SmsListener {

	@Autowired
	private SmsUtil smsUtil;
	
	@JmsListener(destination="sms")
	public void sendSms(Map<String, String> map) {
		try {
			SendSmsResponse response = smsUtil.sendSms(
					map.get("phoneNumbers"), 
					map.get("signName"), 
					map.get("templateCode"), 
					map.get("templateParam"));
			System.out.println("状态码："+response.getCode());
			System.out.println("消息："+response.getMessage());
		} catch (ClientException e) {
			e.printStackTrace();
		}
	}
}
