package com.shinhan.firstzone.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.repository.MemberRepository;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService implements UserDetailsService {
	
	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	PasswordEncoder passEncode;
	
	// 사용자 등록 추가
	public MemberEntity joinUser(MemberEntity member) {
		member.setMpassword(passEncode.encode(member.getMpassword()));
		return memberRepo.save(member);
	}
	
	// 필수인 메서드
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<MemberEntity> memberOp = memberRepo.findById(username);
		MemberEntity member = memberOp.orElse(null);
		
		// MemberEntity → SecurityUser 변경
		UserDetails user = memberOp.filter(m->m!=null).map(m->new SecurityUser(member)).get();
		session.setAttribute("loginMember", member);
		
		return user;
	}

}
