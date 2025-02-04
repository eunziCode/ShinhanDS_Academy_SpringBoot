package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.shinhan.firstzone.entity.BoardEntity;

//Repository설계: DB의 객체와 연결된 entity?BoardEntity, PK의 타입
//JPA가 구현객체를 만들어준다. 
public interface BoardRepository 
        extends CrudRepository<BoardEntity, Long>,
                PagingAndSortingRepository<BoardEntity, Long>,
                QuerydslPredicateExecutor<BoardEntity>{
    
	//1.기본은 CrudRepository를 상속받으면 기본메서드지원 : findAll(), findById(), save()
	//2.규칙에 맞는 함수를 정의하기 
	List<BoardEntity>  findByWriter(String user); //where Writer = ?
	List<BoardEntity>  findByContentLike(String content); //where Content Like ?
	List<BoardEntity>  findByContentContaining(String content);
	                                         //where Content Like '%'||?||'%'
	//where bno >= ? and bno <=?
	List<BoardEntity>  findByBnoGreaterThanEqualAndBnoLessThanEqual(Long bno1, Long bno2) ;
	//where bno between ? and ? 
	List<BoardEntity>  findByBnoBetween(Long bno1, Long bno2) ;
	
	//where title like '%'||?
	//where title like ?||'%'
	//where title like ?||'%' and (bno between ? and ?) order by bno desc 
	List<BoardEntity>  findByTitleStartingWith(String keyword);
	List<BoardEntity>  findByTitleEndingWith(String keyword);
	List<BoardEntity>  findByTitleEndingWithAndBnoBetweenOrderByBnoDesc
	                    (String keyword, Long bno1, Long bno2);
}











