package com.shinhan.firstzone.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.shinhan.firstzone.entity.MemberEntity;

public class SecurityUser extends User {
	
	private static final String ROLE_PREFIX = "ROLE_";

	public SecurityUser(MemberEntity member) {
		super(member.getMid(), member.getMpassword(), makeRole(member));
		System.out.println("----SecurityUser의 생성자(개발자가 추가함)----");
	}
	
	private static Collection<? extends GrantedAuthority> makeRole(MemberEntity member) {
		Collection<GrantedAuthority> roleList = new ArrayList<>();
		roleList.add(new SimpleGrantedAuthority(ROLE_PREFIX + member.getMrole()));
		return roleList;
	}

	public SecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
		System.out.println("----SecurityUser의 생성자(필수)----");
	}

}
