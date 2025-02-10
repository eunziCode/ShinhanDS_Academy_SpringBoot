package com.shinhan.firstzone.twoway2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/replies")
public class WebReplyRestController {

	@Autowired
	WebReplyService replyService;
	
	@Tag(name="Reply API")
	@Operation(summary = "댓글삭제", description = "댓글삭제함")
	@DeleteMapping(value = "/delete/{rno}" )
	public Long delete(@PathVariable Long rno) {
		replyService.deleteReply(rno);
		
		return rno;
	}
	
	@Tag(name="Reply API")
	@Operation(summary = "댓글수정", description = "댓글수정시 title, content만 수정가능")
	@PutMapping(value= "/update", consumes = "application/json")
	public Long update(@RequestBody WebReplyDTO dto) {
		System.out.println(dto);
		
		WebReplyDTO updateReply = replyService.updateReply(dto);
		return updateReply.getRno();
	}
	
	@Tag(name="Reply API")
	@Operation(summary = "댓글등록", description = "댓글입력시 Board번호 자동생성됨, title과 content만 입력")
	@PostMapping(value= "/register", consumes = "application/json")
	public Long insert(@RequestBody WebReplyDTO dto) {
		System.out.println(dto);
		
		WebReplyDTO newReply = replyService.insertReply(dto);
		return newReply.getRno();
	}

	@Tag(name="Reply API")
	@Operation(summary = "댓글조회", description = "댓글조회시 Board번호가 필수")
	@GetMapping("/list/{bno}")
	public List<WebReplyDTO> replyListByBoard(@PathVariable Long bno) {
		WebBoardEntity board = WebBoardEntity.builder().bno(bno).build();
		List<WebReplyDTO> dtoList = replyService.selectByBoard(board);
		
		return dtoList;
	}
}

