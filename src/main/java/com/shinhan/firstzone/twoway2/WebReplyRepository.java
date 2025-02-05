package com.shinhan.firstzone.twoway2;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.shinhan.firstzone.entity.MemberEntity;

// 상수, 추상메서드, static메서드, default메서드, private메서드, 일반메서드 불가
public interface WebReplyRepository extends JpaRepository<WebReplyEntity, Long>,
											QuerydslPredicateExecutor<WebReplyEntity> {

	List<WebReplyEntity> findByReplyer(MemberEntity replyer);
	List<WebReplyEntity> findByBoard(WebBoardEntity board);
	
	public default Predicate makePredicate(String type, String keyword) {
		QWebReplyEntity reply = QWebReplyEntity.webReplyEntity;
		BooleanBuilder builder = new BooleanBuilder();
		
		// where rno >= 0
		builder.and(reply.rno.goe(0));
		if(type==null) return builder;
		switch(type) {
		case "replyText": builder.and(reply.replyText.contains(keyword));break;
		case "replyer": builder.and(reply.replyer.mid.eq(keyword));break;
		}
		
		return builder;
	}
}
