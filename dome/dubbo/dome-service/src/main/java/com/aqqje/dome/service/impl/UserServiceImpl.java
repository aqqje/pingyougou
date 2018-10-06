package com.aqqje.dome.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.aqqje.dome.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Override
	public String getName() {
		return "AqqJe";
	}

}
