package com.shinhan.firstzone.twoway;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

// Repository ← CrudRepository ← PagingAndSortingRepository ← JpaRepository
public interface FreeBoardRepository extends JpaRepository<FreeBoardEntity, Long>,
											 QuerydslPredicateExecutor<FreeBoardEntity> {
	// 1. 기본 제공 메서드 이용 findAll(), findById()
	// JpaRepository → findAll(Pageable)
	// QuerydslPredicateExecutor → findAll(Predicate)
	// 2. 규칙에 맞는 함수정의 추가
	// 특정 writer가 작성한 board 조회
	List<FreeBoardEntity> findByWriter(String ww);
	
	Page<FreeBoardEntity> findByWriter(String ww, Pageable pageable);
	
	
	// 3. JPQL 사용 → 함수 이름 규칙 없어서 마음대로 정의하면 됨
	@Query("select board.title, count(reply)"
			+ "from FreeBoardEntity board "
			+ "join FreeReplyEntity reply on (board = reply.board) "
			+ "group by board.title "
			+ "order by board.title")
	public List<Object[]> selectBoardReplyCount();
	
	// 연관관계 조인은 on 생략가능
	@Query("select board.title, count(reply)"
			+ "from FreeBoardEntity board "
			+ "join board.replies "
			+ "group by board.title "
			+ "order by board.title")
	public List<Object[]> selectBoardReplyCount2();
	
	// nativeQuery는 권장하지 않음
	@Query(value="select board.title, count(reply)"
			+ "from tbl_freeboard board "
			+ "join tbl_free_replies reply on (board.bno = reply.board_bno) "
			+ "group by board.title "
			+ "order by board.title", nativeQuery = true)
	public List<Object[]> selectBoardReplyCount3();
}
