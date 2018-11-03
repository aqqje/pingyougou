package com.pinyougou.search.service.impl;

import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.search.service.ItemSearchService;

@Component
public class ItemSearchListener implements MessageListener{

	@Autowired
	private ItemSearchService itemSearchService;
	
	@Override
	public void onMessage(Message message) {
		System.out.println("监听接收到消息...");
		TextMessage textMessage = (TextMessage)message;
		try {
			 List<TbItem> list = JSON.parseArray(textMessage.getText(), TbItem.class);
			 for(TbItem item: list) {
				 System.out.println(item.getId()+" "+item.getTitle());
				 Map specMap= JSON.parseObject(item.getSpec());//将spec字段中的json字符串转换为map
				 item.setSpcMap(specMap);//给带注解的字段赋值
			 }
			 itemSearchService.updateSolrData(list);//导入	
			System.out.println("成功导入到索引库");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
