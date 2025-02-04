package com.shinhan.firstzone.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString
@MappedSuperclass // table생성안함, 부모class로 사용
@EntityListeners(value = {AuditingEntityListener.class}) //entity객체가 생성/변경되는 것을 감지 
public abstract class BaseEntity {

	@CreatedDate
	@Column(name = "regdate")
	private LocalDateTime regDate;  //insert시에 자동, 카멜표기법:대문자앞에 _가 들어감 reg_date
	
	@LastModifiedDate
	@Column(name = "moddate")
	private LocalDateTime modDate; //insert시, update시에 자동 

	
}
