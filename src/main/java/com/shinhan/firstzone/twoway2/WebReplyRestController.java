package com.shinhan.firstzone.twoway2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/replies")
public class WebReplyRestController {

	@Autowired
	WebReplyService replyService;
	
	
	@PutMapping(value= "/update", consumes = "application/json")
	public Long update(@RequestBody WebReplyDTO dto) {
		System.out.println(dto);
		
		WebReplyDTO updateReply = replyService.updateReply(dto);
		return updateReply.getRno();
	}
	
	@PostMapping(value= "/register", consumes = "application/json")
	public Long insert(@RequestBody WebReplyDTO dto) {
		System.out.println(dto);
		
		WebReplyDTO newReply = replyService.insertReply(dto);
		return newReply.getRno();
	}

	@GetMapping("/list/{bno}")
	public List<WebReplyDTO> replyListByBoard(@PathVariable Long bno) {
		WebBoardEntity board = WebBoardEntity.builder().bno(bno).build();
		List<WebReplyDTO> dtoList = replyService.selectByBoard(board);
		
		return dtoList;
	}
}

