package com.aqqje.dome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aqqje.dome.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	

	@Reference
	private UserService UserService;
	
	@ResponseBody
	@RequestMapping("/showName")
	public String showName() {
		return "aqqje";
	}
}
