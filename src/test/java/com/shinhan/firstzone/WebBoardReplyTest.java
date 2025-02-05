package com.shinhan.firstzone;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.twoway2.WebBoardEntity;
import com.shinhan.firstzone.twoway2.WebBoardRepository;
import com.shinhan.firstzone.twoway2.WebReplyEntity;
import com.shinhan.firstzone.twoway2.WebReplyRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class WebBoardReplyTest {

	@Autowired
	WebBoardRepository boardRepo;
	
	@Autowired
	WebReplyRepository replyRepo;
	
	// [Q7] 동적SQL사용하기
	@Test
	void dynamicSQLBoard() {
		String type="content";
		String keyword="사용7";
		Predicate pre = boardRepo.makePredicate(type, keyword);
		boardRepo.findAll(pre).forEach(board->{
			log.info(board.toString());
		});
	}
	
	// [Q6] 특정board의 reply 정보 조회
//	@Transactional
//	@Test
	void selectReplyByBoard() {
		WebBoardEntity board = WebBoardEntity.builder().bno(5L).build();
		replyRepo.findByBoard(board).forEach(reply->{
			log.info(reply.getRno()
					+", 댓글작성자:"+reply.getReplyer().getMname()
					+", 댓글내용:"+reply.getReplyText()
					+", 게시글제목:"+reply.getBoard().getTitle()
					+", 게시글작성자:"+reply.getBoard().getWriter().getMname()	);
		});
	}
	
	// [Q5] 특정member가 작성한 reply 정보 조회
//	@Test
	void selectByReply() {
		MemberEntity member = MemberEntity.builder().mid("spring3").build();
		replyRepo.findByReplyer(member).forEach(reply->{
			log.info(reply.toString());
		});
	}

	// [Q4] 특정member의 board 정보 조회
//	@Test
	void selectByMember() {
		MemberEntity member = MemberEntity.builder().mid("spring2").build();
		boardRepo.findByWriter(member).forEach(board->{
			log.info(board.getBno()+"--"+board.getTitle()+"--"+
					 board.getWriter().getMname());
		});
	}
	
	
	// [Q3] board 조회
//	@Transactional // fetch = FetchType.LAZY이여도 replies를 select하고자 함
				   // N+1 문제 발생 → board 건수만큼 reply테이블 select문이 생성됨
				   // 해결은 @BatchSize추가 from 댓글 where board_bno in (?,?,?,?,..)
//	@Test
	void selectAll() {
		boardRepo.findAll().forEach(board->{
			log.info(board.getBno()+"--"+board.getTitle()+"--"+board.getReplies().size());
		});
	}
	
	// [Q2] board의 reply 100건 insert(1→10개, 5→5개)
//	@Test
	void insertReply() {
		WebBoardEntity board1 = WebBoardEntity.builder().bno(1L).build();
		WebBoardEntity board5 = WebBoardEntity.builder().bno(5L).build();
		MemberEntity member3 = MemberEntity.builder().mid("spring3").build();
		MemberEntity member4 = MemberEntity.builder().mid("spring4").build();
		
		IntStream.rangeClosed(1, 10).forEach(i->{
			WebReplyEntity reply = WebReplyEntity.builder()
												 .replyText("다대다는 ManyToOne"+i)
												 .replyer(member3)
												 .board(board1)
												 .build();
			replyRepo.save(reply);
		});

		IntStream.rangeClosed(1, 5).forEach(i->{
			WebReplyEntity reply = WebReplyEntity.builder()
												 .replyText("일대다는 OneToMany"+i)
												 .replyer(member4)
												 .board(board5)
												 .build();
			replyRepo.save(reply);
		});
	}
	
	// [Q1] board 100건 insert
//	@Test
	void insertBoard() {
		MemberEntity member1 = MemberEntity.builder().mid("spring1").build();
		MemberEntity member2 = MemberEntity.builder().mid("spring2").build();
		
		IntStream.rangeClosed(1, 100).forEach(i->{
			WebBoardEntity board = WebBoardEntity.builder()
												 .title("web MVC구현하기"+i)
												 .content("JPA사용"+(101-i))
												 .writer(i>50?member1:member2)
												 .build();
			
			boardRepo.save(board);
			
		});
		
	}
	
}
