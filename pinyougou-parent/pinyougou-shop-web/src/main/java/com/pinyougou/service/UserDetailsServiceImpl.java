package com.pinyougou.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;

/**
 * 自定义认证类
 * @author AqqJe
 *
 */
public class UserDetailsServiceImpl implements UserDetailsService {
	
	private SellerService sellerService;
	
	public void setSellerService(SellerService sellerService) {
		this.sellerService = sellerService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<GrantedAuthority> authorities = new ArrayList<>();
		//构建权限列表
		authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));
		//获取商家对象
		TbSeller seller = sellerService.findOne(username);
		String password = seller.getPassword();
		//判断是不是正确的登录用户
		if(seller != null) {
			//审核通过商家可以登录
			if(seller.getStatus().equals("1")) {
				return new User(username, password, authorities);
			}else {
				return null;
			}
		}else {
			return null;
		}
	}

}
