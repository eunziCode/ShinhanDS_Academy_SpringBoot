package com.shinhan.firstzone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinhan.firstzone.entity.MemberEntity;

@Controller
@RequestMapping("/auth")
public class LoginController {

	@GetMapping("/login")
	public void login() {
		
	}
	
	@GetMapping("/loginSuccess")
	public void loginSuccess() {
		
	}
	
	@GetMapping("/logout")
	public void logout() {
		
	}
	
	@GetMapping("/accessDenined")
	public void accessDenined() {
		
	}
	
	// 사용자 등록 페이지 보여주기
	@GetMapping("/signup")
	public String signup() {
		return "auth/joinForm";
	}
	
	@Autowired
	MemberService memberService;
	
	@ResponseBody
	@PostMapping("/joinProc")
	public String joinProc(MemberEntity member) {
		memberService.joinUser(member);
		
		return "register OK";
	}
}
