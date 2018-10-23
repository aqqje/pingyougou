package com.aqqje.dome.rides;

import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:spring/applicationContext-redis.xml")
public class TestSet {
	
	@Autowired
	private RedisTemplate redisTemplate;
	//创建一个集合
	@Test
	public void setValue() {
		redisTemplate.boundSetOps("name").add("aqqje");
		redisTemplate.boundSetOps("name").add("liu");
		redisTemplate.boundSetOps("name").add("li");
	}
	//获取集合的所有值
	@Test
	public void getValue() {
		Set set = redisTemplate.boundSetOps("name").members();
		System.out.println(set);
	}
	//删除整个集合的一个值
	@Test
	public void deleOneValue() {
		redisTemplate.boundSetOps("name").remove("aqqje");
	}
	//删除整个集合
	@Test
	public void deleAllValue() {
		redisTemplate.delete("name");
	}
}
