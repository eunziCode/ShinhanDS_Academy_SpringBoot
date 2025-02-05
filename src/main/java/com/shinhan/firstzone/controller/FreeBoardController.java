package com.shinhan.firstzone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.repository.MemberRepository;
import com.shinhan.firstzone.twoway.FreeBoardRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/freeboard")
public class FreeBoardController {
	
	@Autowired
	FreeBoardRepository boardRepo;
	
	@Autowired
	MemberRepository memberRepo;
	
	// @RequestParam 이용하기
	@GetMapping("/detail2")
	public String f3(Long bno, Model model) {
		model.addAttribute("board", boardRepo.findById(bno).orElse(null));
		
		return "freeboard/boarddetail";
	}

	// @PathVariable 이용하기 - rest 방식
	@GetMapping("/detail/{bno}")
	public String f2(@PathVariable Long bno, Model model) {
		model.addAttribute("board", boardRepo.findById(bno).orElse(null));
		
		return "freeboard/boarddetail";
	}
	
	@GetMapping("/list")
	public String f1(Model model, HttpSession session) {
		model.addAttribute("boardlist", boardRepo.findAll());
		MemberEntity member = memberRepo.findById("spring1").orElse(null);
		session.setAttribute("loginUser", member);
		
		return "freeboard/boardlist";
	}
}
