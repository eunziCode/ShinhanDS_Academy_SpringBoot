package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.shinhan.firstzone.entity.GuestBookEntity;

//interface는 규격서 (정의)
//JPA가 구현체를 만든다. 
//Mapping(DB의 객체(table)와 JAVA의 객체(entity))
//<entity class, PK의 타입>
public interface GuestBookRepository extends CrudRepository<GuestBookEntity, Long>,
                                             PagingAndSortingRepository<GuestBookEntity, Long>,
                                             QuerydslPredicateExecutor<GuestBookEntity>{
      //1.기본제공메서들(CrudRepository) : findAll(), findById(), save(), count()
      //2.규칙에 맞는 함수를 정의(CrudRepository)  findBy???? --- 복잡한문장은 한계가있음
	  //https://docs.spring.io/spring-data/jpa/docs/2.5.1/reference/html/#jpa.query-methods
	  List<GuestBookEntity> findByWriter(String writer); 
	  List<GuestBookEntity> findByContentContaining(String content2);
	  List<GuestBookEntity> findByGnoGreaterThan(Long gno); 
	  //3.JPQL(JPA Query language)...CrudRepository	  
	  @Query(" select book"
	  		+ " from GuestBookEntity book"
	  		+ " where book.writer = ?1"
	  		+ " and book.content like %?2% "
	  		+ " order by book.gno desc")
	  List<GuestBookEntity> findByWriterContent2(String writer, String content); 
	  @Query(" select book "
		  		+ " from GuestBookEntity book "
		  		+ " where book.writer = :ww"
		  		+ " and book.content like %:cc% "
		  		+ " order by book.gno desc")
	  List<GuestBookEntity> findByWriterContent3(@Param("ww") String writer,
				                                 @Param("cc") String content); 
	  
	  @Query(" select book "
		  		+ " from #{#entityName} book "
		  		+ " where book.writer = :ww"
		  		+ " and book.content like %:cc% "
		  		+ " order by book.gno desc")
	  List<GuestBookEntity> findByWriterContent4(@Param("ww") String writer,
				                                 @Param("cc") String content);
	  
	  @Query(" select book.gno, book.title, book.content "
		  		+ " from #{#entityName} book "
		  		+ " where book.writer = :ww"
		  		+ " and book.content like %:cc% "
		  		+ " order by book.gno desc")
	  List<Object[]> findByWriterContent5(@Param("ww") String writer,
				                          @Param("cc") String content);
	  
	  @Query(value = " select * from t_guestbook book "
		  		+ " where book.writer = :ww"
		  		+ " and book.content like %:cc% "
		  		+ " order by book.gno desc" ,		  
			  nativeQuery = true)
	  List<GuestBookEntity> findByWriterContent6(@Param("ww") String writer,
				                                 @Param("cc") String content);
	  
	  
}










