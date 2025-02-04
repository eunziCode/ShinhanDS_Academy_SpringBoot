package com.shinhan.firstzone;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.firstzone.twoway.FreeBoardEntity;
import com.shinhan.firstzone.twoway.FreeBoardRepository;
import com.shinhan.firstzone.twoway.FreeReplyEntity;
import com.shinhan.firstzone.twoway.FreeReplyRepository;
import com.shinhan.firstzone.twoway.QFreeBoardEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class TwoWayTest {

	@Autowired
	FreeBoardRepository boardRepo;
	
	@Autowired
	FreeReplyRepository replyRepo;
	
	// 동적SQL로 조회
	@Test
	public void dynamicSQL() {
		// type, keyword에 의해 동적으로 SQL문을 만들기
		String type="content";
		String keyword="의견";
		QFreeBoardEntity board = QFreeBoardEntity.freeBoardEntity;
		BooleanBuilder builder = new BooleanBuilder();
		
		switch(type) {
		case "title": builder.and(board.title.like("%"+keyword+"%"));break; // where title like ?
		case "writer": builder.and(board.writer.like("%"+keyword+"%"));break;
		case "content": builder.and(board.content.like("%"+keyword+"%"));break;
		}
		
		builder.and(board.bno.gt(0L)); // and bno > 0
		boardRepo.findAll(builder).forEach(b->{
			log.info(b.toString());
		});
	}
	
	// 전체 댓글 조회
	//@Transactional
	//@Test
	public void q7() {
		replyRepo.findAll().forEach(reply->{
			log.info(reply.toString());
			log.info("board:"+reply.getBoard().toString());
		});
	}
	
	// [Q6_2] 1번째 page 3건 조회, bno desc sort (1page는 10건임)
	//@Test
	public void getWriter() {
		String writer = "user1";
		int page = 1;
		String[] property = {"bno"};
		Pageable pageable = PageRequest.of(page - 1, 2, Sort.by(Direction.DESC, property));
		Page<FreeBoardEntity> result = boardRepo.findByWriter(writer, pageable);
		
		System.out.println("getNumber:"+result.getNumber()); // 현재 페이지 index
		System.out.println("getSize:"+result.getSize()); // 페이지당 data 개수
		System.out.println("getTotalElements:"+result.getTotalElements()); // 전체 data 개수
		System.out.println("getTotalPages:"+result.getTotalPages()); // 전체 page 개수
		
		result.getContent().forEach(board->{
			System.out.println(board);
		});
	}
	
	// [Q6] 1번째 page 3건 조회, bno desc sort (1page는 10건임)
	//@Test
	public void q6() {
		int page = 1;
		String[] property = {"bno"};
		Pageable pageable = PageRequest.of(page - 1, 3, Sort.by(Direction.DESC, property));
		Page<FreeBoardEntity> result = boardRepo.findAll(pageable);
		
		System.out.println("getNumber:"+result.getNumber()); // 현재 페이지 index
		System.out.println("getSize:"+result.getSize()); // 페이지당 data 개수
		System.out.println("getTotalElements:"+result.getTotalElements()); // 전체 data 개수
		System.out.println("getTotalPages:"+result.getTotalPages()); // 전체 page 개수
		
		result.getContent().forEach(board->{
			System.out.println(board);
		});
	}
	
	// [Q5_2] Board의 댓글 갯수 → JPQL을 이용하기(SQL문장 수를 줄이기 위함)
	//@Test
	public void q5_2() {
		boardRepo.selectBoardReplyCount().forEach(arr->{
			log.info(Arrays.toString(arr));
		});
		System.out.println("---------------");
		boardRepo.selectBoardReplyCount2().forEach(arr->{
			log.info(Arrays.toString(arr));
		});
		System.out.println("---------------");
		boardRepo.selectBoardReplyCount3().forEach(arr->{
			log.info(Arrays.toString(arr));
		});
	}

	// [Q5] Board의 댓글 갯수 → select가 2번
	// @BatchSize(size=100)이 없으면 board 개수만큼 reply를 select
	//@Transactional
	//@Test
	public void q5() {
		boardRepo.findAll().forEach(board->{
			log.info("제목:"+board.getTitle());
			log.info("댓글갯수:"+board.getReplies().size());
		});
	}
	
	// user1이 작성한 board정보 조회
	//@Transactional
	//@Test
	public void selectByWriter() {
		String writer = "user1";
		boardRepo.findByWriter(writer).forEach(board->{
			log.info(board.getBno()+"--"+board.getTitle()+"--"+board.getContent()+"--"+board.getReplies().size());
		});
	}
	
	// 댓글의 board가 null인 data 조회 후 board 채우기
	//@Test
	public void selectReplyNull() {
		FreeBoardEntity board107 = FreeBoardEntity.builder().bno(107L).build();
		replyRepo.findByBoardIsNull().forEach(reply->{
			log.info(reply.toString());
			reply.setBoard(board107);
			replyRepo.save(reply);
		});
	}
	
	// 특정 board에 댓글 추가
	//@Test
	public void insertReply2() {
		FreeBoardEntity board107 = FreeBoardEntity.builder().bno(107L).build();
		IntStream.rangeClosed(1, 5).forEach(i->{
			FreeReplyEntity reply = FreeReplyEntity.builder()
												   .reply("신한DS아카데미"+i)
												   .replyer("user7")
												   .board(board107) // 특정board의 댓글
												   .build();
			replyRepo.save(reply);
		});
	}
	
	// 댓글 추가(의도적으로 board null 부여)
	//@Test
	public void insertReply() {
		IntStream.rangeClosed(1, 5).forEach(i->{
			FreeReplyEntity reply = FreeReplyEntity.builder()
												   .reply("신한DS"+i)
												   .replyer("user7")
												   .build();
			replyRepo.save(reply);
		});
	}
	
	// [Q4] reply의 참조 컬럼이 비어있음
	//@Test
	public void q4() {
		Long[] arr = {11L, 12L, 13L, 14L, 15L, 16L, 17L, 18L, 19L, 20L};
		
		// 댓글들 가져오기
		List<FreeReplyEntity> replies = replyRepo.findAllById(Arrays.asList(arr));
		
		// N번 board 가져오기
		FreeBoardEntity entity = boardRepo.findById(4L).orElse(null);
		
		if(entity != null) {
			// N번 board의 reply둘
			List<FreeReplyEntity> boardReplies = entity.getReplies();
			
			// reply의 board가 null값들의 board를 수정하기
			replies.forEach(reply->{
				reply.setBoard(entity);
				boardReplies.add(reply);
			});
			entity.setContent("----댓글이 추가됨----");
			entity.setTitle("Cappuccino~");
			boardRepo.save(entity);
		}
	}
	
	// [Q3] 특정board 1건 조회, board의 reply 추가
	//@Test
	public void q3() {
		Long bno = 109L;
		boardRepo.findById(bno).ifPresent(board->{
			log.info("before:"+board.toString());
			
			// 댓글 5개 추가
			List<FreeReplyEntity> replies = board.getReplies();
			IntStream.rangeClosed(1,3).forEach(i->{
				FreeReplyEntity reply =  FreeReplyEntity.builder()
														.reply("읏추:("+i)
														.replyer("user8")
														.board(board)
														.build();
				replies.add(reply);
			});
			board.setContent("---의견있음---");
			boardRepo.save(board);
		});
	}
	
	// board 전체 삭제
	//@Test
	void deleteBoard() {
		boardRepo.deleteAll();
	}
	
	// [Q2] board 모두 조회
	//@Test
	public void q2() {
		boardRepo.findAll().forEach(board->{
			log.info(board.getBno()+"---"+board.getTitle());
			board.getReplies().forEach(reply->{
				log.info("댓글:"+reply.toString());
			});
		});
	}
	
	// [Q1] board 100건 insert
	//@Test
	public void q1() {
		IntStream.rangeClosed(1, 10).forEach(i->{
			FreeBoardEntity board = FreeBoardEntity.builder()
												   .writer("user"+(i%3+1))
												   .title("JPA 재밌당"+i)
												   .content("JPA 이용 양방향 연습~"+(11-i))
												   .build();
			boardRepo.save(board);
		});
	}
}
