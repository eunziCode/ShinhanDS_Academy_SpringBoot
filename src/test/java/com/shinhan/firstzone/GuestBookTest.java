package com.shinhan.firstzone;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shinhan.firstzone.entity.GuestBookEntity;
import com.shinhan.firstzone.entity.QGuestBookEntity;
import com.shinhan.firstzone.repository.GuestBookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class GuestBookTest {

	@Autowired
	GuestBookRepository questRepo;

	// @Test
	public void insert() {
		IntStream.rangeClosed(1, 20).forEach(i -> {
			GuestBookEntity entity = GuestBookEntity.builder().title("금요일" + i).content("내일은 토요일" + (21 - i))
					.writer("작성자" + (i % 3) + 1).build();
			questRepo.save(entity);
		});
	}

	// @Test
	public void selectAll() {
		List<GuestBookEntity> booklist = (List<GuestBookEntity>) questRepo.findAll();

		booklist.forEach(book -> {
			log.info(book.toString());
		});
	}

	// @Test
	public void selectById() {
		Long gno = 10L;
		GuestBookEntity entity = questRepo.findById(gno).orElse(null);
		log.info(entity == null ? "not found" : entity.toString());

		questRepo.findById(gno).ifPresent(book -> {
			book.setTitle(book.getTitle() + "수정");
			book.setContent("-----일요일도 좋아~~~~~~~~~");
			book.setWriter("manager");
			GuestBookEntity updateEntity = questRepo.save(book);
			log.info("수정된book:" + updateEntity.toString());
		});
	}

	// @Test
	public void delete() {
		Long gno = 11L;
		log.info("before count:" + questRepo.count() + "건");
		questRepo.deleteById(gno);
		log.info("after count:" + questRepo.count() + "건");
	}

	// @Test
	public void select() {
		String writer = "작성자11";
		questRepo.findByWriter(writer).forEach(book -> {
			log.info(book.toString());
		});
		String content = "토요일1";
		questRepo.findByContentContaining(content).forEach(book -> {
			log.info(book.toString());
		});
		Long gno = 17L;
		questRepo.findByGnoGreaterThan(gno).forEach(book -> {
			log.info(book.toString());
		});
	}

	// @Test
	public void select2() {
		String writer = "작성자11";
		String content = "토요일2";
		questRepo.findByWriterContent2(writer, content).forEach(book -> {
			log.info("2:" + book.toString());
		});
		questRepo.findByWriterContent3(writer, content).forEach(book -> {
			log.info("3:" + book.toString());
		});
		questRepo.findByWriterContent4(writer, content).forEach(book -> {
			log.info("4:" + book.toString());
		});

		questRepo.findByWriterContent5(writer, content).forEach(arr -> {
			log.info("5:" + Arrays.toString(arr));
		});
		questRepo.findByWriterContent6(writer, content).forEach(book -> {
			log.info("6:" + book.toString());
		});
	}

	// 동적 SQL문 생성
	// @Test
	public void dynamicSQL1() {
		String writer = "작성자11";
		String content = "%토요일%";
		String type = "writer"; // where writer = ?, where content like ?
		LocalDateTime dt = LocalDateTime.of(2025, 1, 30, 0, 0, 0);

		BooleanBuilder builder = new BooleanBuilder();
		QGuestBookEntity book = QGuestBookEntity.guestBookEntity;

		switch (type) {
		case "writer":
			builder.and(book.writer.eq(writer));
			break;
		case "content":
			builder.and(book.content.like(content));
			break;
		}

		builder.and(book.gno.gt(15L)); // and gno > 15
		builder.and(book.regDate.goe(dt)); // and regDate>= ?
		System.out.println(builder);

		questRepo.findAll(builder).forEach(b -> log.info(b.toString()));

	}

	@Autowired
	JPAQueryFactory queryFactory;	
	// 동적 SQL문 생성
	@Test
	public void dynamicSQL2() {
		String writer = "작성자11";
		String content = "%토요일%";
		String type = "content"; // where writer = ?, where content like ?
		LocalDateTime dt = LocalDateTime.of(2025, 1, 30, 0, 0, 0);

		QGuestBookEntity book = QGuestBookEntity.guestBookEntity;
		List<GuestBookEntity> booklist = null;
		switch (type) {
		case "writer":
			booklist = queryFactory.selectFrom(book).where(book.writer.eq(writer)).fetch();
			break;
		case "content":
			booklist = queryFactory.selectFrom(book).where(book.content.like(content)).fetch();
			break;
		}
		booklist.forEach(b -> log.info(b.toString()));
		 

	}

}
