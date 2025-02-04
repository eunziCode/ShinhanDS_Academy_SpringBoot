package com.shinhan.firstzone.twoway;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.shinhan.firstzone.onetomany.PDSBoardEntity;
import com.shinhan.firstzone.onetomany.PDSFileEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString(exclude = {"replies"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_freeboard")
public class FreeBoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
	
	//@NonNull // 자바에서 FreeBoardEntity를 만들때 not null하게 설정
	@Column(nullable = false) // DB 설정
	private String title;
	private String writer;
	private String content;
	
	@CreationTimestamp // insert시 자동 값(insert 시각)
	private Timestamp regDate;
	
	@UpdateTimestamp // insert시 자동 값(insert 시각), update시 자동값(update 시각)
	private Timestamp updateDate;
	
	// FetchType.EAGER인 경우 SQL문 수 줄이기
	@BatchSize(size = 100)
	@OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	List<FreeReplyEntity> replies;
}
