package com.shinhan.firstzone.onetoone;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_usercellphone3")
public class UserCellPhoneEntity3 {
	@Id // entity 생성시 pk가 있어야하므로 임의로 생성
	String aa;
	
	// 식별자(주 테이블의 키가 부 테이블의 키로 사용)
	@MapsId // UserEntity3의 user_id를 mapping → PK 겸 FK가 되는 것
	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "user_id")
	UserEntity3 user3;
	
	String phoneNumber;
	String model;
	
}