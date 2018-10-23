package com.aqqje.dome.rides;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * 右压栈：后添加的对象排在后边
 * @author Administrator
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath*:spring/applicationContext-redis.xml")
public class TestList {

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Test
	public void setValue() {
		redisTemplate.boundListOps("newName").rightPush("中国1");
		redisTemplate.boundListOps("newName").rightPush("中国2");
		redisTemplate.boundListOps("newName").rightPush("中国3");
		redisTemplate.boundListOps("newName").rightPush("中国4");
	}
	//显示右压栈集合
	@Test
	public void getValue() {
		List list = redisTemplate.boundListOps("newName").range(0, 10);
		for(int i=0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	//左压栈 ==> leftpush
	
	//查询集合某个元素
	@Test
	public void findOne() {
		String str = (String)redisTemplate.boundListOps("newName").index(0);
		System.out.println(str);
	}
	
	//移除集合某个元素
	@Test
	public void dele() {
		redisTemplate.boundListOps("newName").remove(1, "中国2");
	}
	
	
}
