package com.shinhan.firstzone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.firstzone.dto.GuestBookDTO;
import com.shinhan.firstzone.service.GuestBookService;

@RestController
@RequestMapping("/guest")
public class GuestBookController {

	@Autowired
	GuestBookService gService;
	
	@GetMapping("/list")
	public List<GuestBookDTO> selectAll(){
		return gService.selectAll();
	}
	
	@GetMapping("/list2")
	public List<GuestBookDTO> selectAll2(){
		return gService.selectAll2();
	}
}









