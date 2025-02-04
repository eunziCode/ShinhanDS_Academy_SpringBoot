package com.shinhan.firstzone.twoway;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FreeReplyRepository extends JpaRepository<FreeReplyEntity, Long> {
	
	// 1. JpaRepository가 제공하는 기본 메서드 사용하기 : findById() ...등
	// 2. 규칙에 맞는 함수 사용하기
	// 댓글의 board가 null인 data 조회
	public List<FreeReplyEntity> findByBoardIsNull();
}
