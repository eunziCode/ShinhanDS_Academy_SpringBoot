package com.shinhan.firstzone.twoway2;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.entity.MemberEntity;

public class WebReplyService {

	@Autowired
	WebReplyRepository replyRepo;
	
	// 조회 - 전체
	public List<WebReplyEntity> selectAll() {
		return replyRepo.findAll();
	}
	
	// 조회 - 특정 rno의 상세보기
	public WebReplyEntity selectById(Long rno) {
		return replyRepo.findById(rno).orElse(null);
	}

	// 조회 - 특정member가 작성한 reply
	public List<WebReplyEntity> selectByMember(MemberEntity member) {
		return replyRepo.findByReplyer(member);
	}	
	
	// 조회 - 특정board의 reply
	public List<WebReplyEntity> selectByBoard(WebBoardEntity board) {
		return replyRepo.findByBoard(board);
	}	
	
	// 조회 - 동적SQL
	public List<WebReplyEntity> dynamicSQL(String type, String keyword) {
		Predicate pre = replyRepo.makePredicate(type, keyword);
		return (List<WebReplyEntity>)replyRepo.findAll(pre);
	}
	
	// 입력
	public WebReplyEntity insertBoard(WebReplyEntity reply) {
		 return replyRepo.save(reply);
	}
	
	// 수정
	public WebReplyEntity updateBoard(WebReplyEntity reply) {
		return replyRepo.save(reply);
	}
	
	// 삭제 - 단일
	public void deleteBoard(Long rno) {
		replyRepo.deleteById(rno);
	}

	// 삭제 - 복수
	public void deleteBoards(List<Long> rnoList) {
		replyRepo.deleteAllById(rnoList);
	}
	
	// 삭제 - 전체
	public void deleteAll() {
		replyRepo.deleteAll();
	}
}
