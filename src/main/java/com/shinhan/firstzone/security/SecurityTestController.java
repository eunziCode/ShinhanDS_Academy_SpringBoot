package com.shinhan.firstzone.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/security")
public class SecurityTestController {

	@GetMapping("/all")
	public void f1() {
		log.info("모든 사용자가 접근 가능");
	}
	
	@GetMapping("/user")
	public void f2() {
		log.info("user 권한 사용자가 접근 가능");
	}
	
	@GetMapping("/admin")
	public void f3() {
		log.info("admin 권한 사용자가 접근 가능");
	}
	
	@GetMapping("/manager")
	public void f4() {
		log.info("manager 권한 사용자가 접근 가능");
	}
}
