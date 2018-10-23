package com.aqqje.dome.rides;

import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:spring/applicationContext-redis.xml")
public class TestHash {

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Test
	public void setValue() {
		redisTemplate.boundHashOps("hash").put("a", "soft1");
		redisTemplate.boundHashOps("hash").put("c", "soft2");
		redisTemplate.boundHashOps("hash").put("b", "soft3");
		redisTemplate.boundHashOps("hash").put("d", "soft4");
		redisTemplate.boundHashOps("hash").put("e", "soft5");
	}
	
	//获取所有key
	@Test
	public void getKeys() {
		Set set = redisTemplate.boundHashOps("hash").keys();
		System.out.println(set);
	}
	//获取所有的值
	@Test
	public void getValues() {
		List list = redisTemplate.boundHashOps("hash").values();
		System.out.println(list);
	}
	//根据key取值
	@Test
	public void getValueByKey() {
		String str = (String)redisTemplate.boundHashOps("hash").get("a");
		System.out.println(str);
	}
	//根据key移除值
	@Test
	public void deleValueByKey() {
		redisTemplate.boundHashOps("hash").delete("a");
	}
}
