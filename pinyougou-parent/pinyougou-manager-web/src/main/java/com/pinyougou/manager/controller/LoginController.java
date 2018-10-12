package com.pinyougou.manager.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户登录
 * @author AqqJe
 *
 */
@RestController
@RequestMapping("/login")
public class LoginController {

	@RequestMapping("/getLoginName")
	public Map<String, String> getLoginName(){
		Map<String,String> map = new HashMap<>();
		String longName = SecurityContextHolder.getContext().getAuthentication().getName();
		map.put("loginName", longName);
		return map;
	}
}
