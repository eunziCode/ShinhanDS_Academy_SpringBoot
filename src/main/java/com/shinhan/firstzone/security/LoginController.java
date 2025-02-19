package com.shinhan.firstzone.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.entity.MemberRole;
import com.shinhan.firstzone.security.jwt.AuthServiceImpl;
import com.shinhan.firstzone.security.jwt.TokenDTO;

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
	//------------------------------------------------------------
	@Autowired
	AuthServiceImpl authService;
	
	@PostMapping("/login")
	@ResponseBody
	public ResponseEntity<TokenDTO> getMemberProfile(@RequestBody MemberEntity request) {
		System.out.println(request);
		String token = authService.login(request);
		TokenDTO dto = TokenDTO.builder().login(request.getMid()).token(token).build();
		return new ResponseEntity<>(dto, HttpStatus.OK);
	}
	@PostMapping("/signup")
	@ResponseBody
	public ResponseEntity<MemberEntity> f7(@RequestBody MemberEntity member) {
//		member.setMrole(MemberRole.USER);
		MemberEntity newMember = memberService.joinUser(member);
		return new ResponseEntity<>(newMember, HttpStatus.OK);
	}
}
