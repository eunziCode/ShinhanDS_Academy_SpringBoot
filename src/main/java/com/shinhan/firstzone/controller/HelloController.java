package com.shinhan.firstzone.controller;

import java.util.Arrays;
import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shinhan.firstzone.twoway.FreeBoardEntity;

@Controller
@RequestMapping("/section1")
public class HelloController {
	
//	@GetMapping("/ex3")
//	public String f6() {
//		return "layout/basicLayout";
//	}
	
	@GetMapping("/ex2")
	public String f5() {
		return "layout/exTemplate";
	}
	
	@GetMapping("/ex1")
	public String f4() {
		return "layout/exLayout1";
	}
	
	@GetMapping("/hello3")
	public void f3(Model model) {
		model.addAttribute("now", new Date());
		model.addAttribute("price", 123456789);
		model.addAttribute("title", "This is a just sample");
		model.addAttribute("options", Arrays.asList("aa","bb","cc","dd"));
	}
	
	@GetMapping("/hello1")
	public void f1(Model model) {
		model.addAttribute("myname", "eunji");
		model.addAttribute("myscore", 99);
		FreeBoardEntity board = FreeBoardEntity.builder()
											   .title("스프링부트")
											   .content("타임리프 사용하기")
											   .writer("eunji")
											   .build();
		model.addAttribute("board",board);
		
	}

	@GetMapping("/hello2")
	public String f2() {
		return "section1/hello1"; // template/section1/hello1.html
	}
}
