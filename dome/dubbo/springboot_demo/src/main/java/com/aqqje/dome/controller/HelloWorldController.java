package com.aqqje.dome.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@Autowired
	private Environment evn; 
	
	@RequestMapping("/hello")
	public String helloWorld() {
		return "<div align=\"center\"><h1>LIULI 520 "+evn.getProperty("url")+"</h1></div>";
	}
}
