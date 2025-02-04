package com.shinhan.firstzone.twoway;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.shinhan.firstzone.onetomany.PDSBoardEntity;
import com.shinhan.firstzone.onetomany.PDSFileEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(exclude = "board")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_free_replies")
public class FreeReplyEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;
	private String reply;
	private String replyer;
	
	@CreationTimestamp // insert시 자동 값(insert 시각)
	private Timestamp regDate;
	
	@UpdateTimestamp // insert시 자동 값(insert 시각), update시 자동값(update 시각)
	private Timestamp updateDate;
	
	// 연관관계 → 여러 개의 댓글은 하나의 board를 참조
	// 컬럼은 n쪽에 만들어짐 즉, 댓글에 생성되는 것
	@ManyToOne(fetch = FetchType.LAZY)
	private FreeBoardEntity board; // 컬럼이름은 board_bno
}
