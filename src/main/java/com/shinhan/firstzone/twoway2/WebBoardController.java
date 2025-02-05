package com.shinhan.firstzone.twoway2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shinhan.firstzone.entity.MemberEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/webboard")
public class WebBoardController {

	@Autowired
	WebBoardService boardService;
	
	@PostMapping("/update")
	public String update(WebBoardEntity board, String mid) {
		MemberEntity member = MemberEntity.builder().mid(mid).build();
		board.setWriter(member);
		log.info(board.toString());
		boardService.updateBoard(board);
		return "redirect:list";
	}
	
	@GetMapping("/detail")
	public void selectById(Long bno, Model model) {
		model.addAttribute("board", boardService.selectById(bno));
	}
	
	@GetMapping("/detail2/{bno}")
	public String selectById2(@PathVariable Long bno, Model model) {
		model.addAttribute("board", boardService.selectById(bno));
		return "webboard/detail";
	}
	
	@GetMapping("/list")
	public void selectAll(Model model) {
		model.addAttribute("boardlist", boardService.selectAll());
	}
}
