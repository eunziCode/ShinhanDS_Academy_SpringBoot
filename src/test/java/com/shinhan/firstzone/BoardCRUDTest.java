package com.shinhan.firstzone;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.firstzone.entity.BoardEntity;
import com.shinhan.firstzone.entity.QBoardEntity;
import com.shinhan.firstzone.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class BoardCRUDTest {

	@Autowired
	BoardRepository boardRepo; //BoardRepository인터페이스 구현 class가 Injection된다. 
	
	
	@Test
    void dynamicSQLTest() {
	    String title2 = "제목"; //and title like '%제목%'
	    Long bno2 = 100L;  //and bno>100
	    
	    BooleanBuilder builder = new BooleanBuilder();
	    QBoardEntity board = QBoardEntity.boardEntity;
	    builder.and(board.title.like("%"+title2+"%"));
	    builder.and(board.bno.gt(bno2));
	    builder.and(board.writer.eq("작성자1"));    
	    System.out.println(builder);
	    //findAll() =>CrudRepository에서 제공
	    //findAll(predicate)=>QuerydslPredicateExecutor에서 제공 
	    List<BoardEntity> blist = (List<BoardEntity>)boardRepo.findAll(builder);
	    blist.forEach(b->{
	    log.info(b.toString());
	    });
    }

	//@Test
	public void f6() {
		//페이지번호는 0부터 시작
		//Pageable pageable = PageRequest.of(0, 5);
		//Pageable pageable = PageRequest.of(0, 5, Sort.by("bno").ascending());
		
		String[] columns = {"writer","bno"};
		Pageable pageable = PageRequest.of(0, 7, Sort.Direction.DESC, columns);
		Page<BoardEntity> result = boardRepo.findAll(pageable);
		
		log.info("getSize:"+ result.getSize());
		log.info("getNumber:"+ result.getNumber()); 
		log.info("getTotalPages:"+ result.getTotalPages()); 
		log.info("getTotalElements:"+ result.getTotalElements()); 
	    List<BoardEntity> blist = result.getContent();
	    blist.forEach(board->{
	    	log.info(board.toString());
	    });
	}
	
	
	
	//@Test
	public void f5() {
		boardRepo.findByTitleEndingWithAndBnoBetweenOrderByBnoDesc("7",27L,67L).forEach(board->{
			log.info(board.toString());
		});
		 
	}
	
	//@Test
	public void f4() {
		boardRepo.findByTitleEndingWith("7").forEach(board->{
			log.info(board.toString());
		});
		 
	}
	
	//@Test
	public void f3() {
		boardRepo.findByTitleStartingWith("토").forEach(board->{
			log.info(board.toString());
		});
		 
	}
	
	//@Test
	public void f2() {
		boardRepo.findByBnoGreaterThanEqualAndBnoLessThanEqual(10L,15L).forEach(board->{
			log.info(board.toString());
		});
		log.info("----------findByBnoBetween----------------------");
		boardRepo.findByBnoBetween(10L,15L).forEach(board->{
			log.info(board.toString());
		});
	}
	
	//@Test
	public void f1() {
		boardRepo.findByContentLike("%말자%").forEach(board->{
			log.info(board.toString());
		});
	}
	
	//@Test
	public void selectByContent() {
		boardRepo.findByContentContaining("말자").forEach(board->{
			log.info(board.toString());
		});
	}
	
	//@Test
	public void selectByWriter() {
		boardRepo.findByWriter("user7").forEach(board->{
			log.info(board.toString());
			board.setContent("졸지말자!!");
			boardRepo.save(board);
		});
	}
	
	//@Test
	public void delete() {
		log.info("delete before:"+ boardRepo.count());
		boardRepo.deleteById(99L);
		log.info("delete after:"+ boardRepo.count());
	}
	
	
	
	//@Test
	public void update() {
		boardRepo.findById(90L).ifPresentOrElse(b->{
			log.info("찾음:" + b.toString());
			b.setTitle("제목변경");
			b.setContent("금요일은 강점교육");
			b.setWriter("admin");
			boardRepo.save(b); //입력과 수정은 save함수이용
		}, ()->{
			log.info("못찾음");
		});
		BoardEntity board = boardRepo.findById(90L).orElse(null);
		log.info(board==null?"not found":board.toString());
	}
	
	
	//@Test
	public void selectById() {
		BoardEntity board = boardRepo.findById(90L).orElse(null);
		log.info(board==null?"not found":board.toString());
		
		BoardEntity board2 = boardRepo.findById(190L).orElse(null);
		log.info(board2==null?"not found":board2.toString());
		
		boardRepo.findById(190L).ifPresentOrElse(b->{
			log.info("찾음:" + b.toString());
		}, ()->{
			log.info("못찾음");
		});
		
	}
	
	//@Test
	public void selectAll() {
		boardRepo.findAll().forEach(board->{
			log.info(board.toString());
		});
	}
	
	//@Test
	public void insert2() {
		 
		BoardEntity board = BoardEntity.builder()
				.title("토요일")
				.content("놀자~~")
				.writer("member")
				.build();
		boardRepo.save(board);//insert into~~~
		 
	}
	
	//@Test
	public void insert() {
		IntStream.rangeClosed(1, 100).forEach(i->{
			BoardEntity board = BoardEntity.builder()
					.title("oracle목요일"+i)
					.content("배고파~~")
					.writer("user" + (i%10+1))
					.build();
			boardRepo.save(board);//insert into~~~
		});
	}
	
}



