package com.pinyougou.shop.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商家登录捷控制
 * @author AqqJe
 *
 */
@RestController
@RequestMapping("/login")
public class LoginController {
	
	@RequestMapping("/getLoginName")
	public Map<String, String> getLoginName() {
		Map<String, String> map = new HashMap<>();
		String loginName = SecurityContextHolder.getContext().getAuthentication().getName();
		map.put("loginName", loginName);
		return map;
	}
}
