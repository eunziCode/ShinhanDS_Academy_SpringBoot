package com.shinhan.firstzone.entity;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter@ToString
@Builder
@Entity
@Table(name = "t_board")
public class BoardEntity {
	@Id  //PK(Primary key)필수임 
	@GeneratedValue(strategy = GenerationType.IDENTITY) //oracle:SEQUENCE, mysql:IDENTITY
	private Long bno;
	
	@NonNull  //자바에서 필수field임 
	@Column(nullable = false) //DB의 칼럼이 not null 
	private String title;
	
	@Column(length = 100)
	private String content;
	private String writer;
	
	@CreationTimestamp  //insert시 자동으로 시각이 입력
	private Timestamp regDate; //reg_date
	
	@UpdateTimestamp //insert시, update시에 자동으로 시각이 입력
	private Timestamp updateDate; //update_date
}
