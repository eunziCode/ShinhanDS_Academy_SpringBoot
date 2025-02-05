package com.shinhan.firstzone.twoway2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.entity.MemberEntity;

// 상수, 추상메서드, static메서드, default메서드, private메서드, 일반메서드 불가
public interface WebBoardRepository extends JpaRepository<WebBoardEntity, Long>,
											QuerydslPredicateExecutor<WebBoardEntity> {

	// 규칙에 맞는 함수이름 정의
	List<WebBoardEntity> findByWriter(MemberEntity writer);
	
	public default Predicate makePredicate(String type, String keyword) {
		QWebBoardEntity board = QWebBoardEntity.webBoardEntity;
		BooleanBuilder builder = new BooleanBuilder();
		
		// where bno >= 0
		builder.and(board.bno.goe(0));
		if(type==null) return builder;
		switch(type) {
		case "title": builder.and(board.title.contains(keyword));break;
		case "content": builder.and(board.content.contains(keyword));break;
		case "writer": builder.and(board.writer.mid.eq(keyword));break;
		}
		
		return builder;
	}
}
