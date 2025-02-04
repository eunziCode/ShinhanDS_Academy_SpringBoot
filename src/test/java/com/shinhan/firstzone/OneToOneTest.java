package com.shinhan.firstzone;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.onetoone.QUserCellPhoneEntity3;
import com.shinhan.firstzone.onetoone.UserCellPhoneEntity;
import com.shinhan.firstzone.onetoone.UserCellPhoneEntity2;
import com.shinhan.firstzone.onetoone.UserCellPhoneEntity3;
import com.shinhan.firstzone.onetoone.UserCellPhoneRepository;
import com.shinhan.firstzone.onetoone.UserCellPhoneRepository2;
import com.shinhan.firstzone.onetoone.UserCellPhoneRepository3;
import com.shinhan.firstzone.onetoone.UserEntity;
import com.shinhan.firstzone.onetoone.UserEntity2;
import com.shinhan.firstzone.onetoone.UserEntity3;
import com.shinhan.firstzone.onetoone.UserRepository;
import com.shinhan.firstzone.onetoone.UserRepository2;
import com.shinhan.firstzone.onetoone.UserRepository3;

@SpringBootTest
public class OneToOneTest {

	// 주 table을 이용해서 CRUD
	@Autowired
	UserRepository userRepo;
	@Autowired
	UserCellPhoneRepository phoneRepo;

	// 부 table을 이용해서 CRUD
	@Autowired
	UserRepository2 userRepo2;
	@Autowired
	UserCellPhoneRepository2 phoneRepo2;
	
	// 부 테이블에서 주 테이블의 키를 식별자로 사용하기
	@Autowired
	UserRepository3 userRepo3;
	@Autowired
	UserCellPhoneRepository3 phoneRepo3;
	
	// 부 테이블에서 주 테이블의 키를 식별자로 사용하기
//	@Test
	void f4() {
		UserCellPhoneEntity3 phone = UserCellPhoneEntity3.builder()
														 .phoneNumber("010-8989-6666")
														 .model("갤럭시A20")
														 .build();
		UserEntity3 user = UserEntity3.builder().userid("dark").username("뇽뇽").phone(phone).build();
		phone.setUser3(user); // 주의! user에 cascade = CascadeTye.ALL 필요
		userRepo3.save(user);
	}
	
	// 부 table을 이용해서 주 table에 insert
//	@Test
	void f3() {
		UserEntity2 user = UserEntity2.builder()
									  .userid("dark")
									  .username("뇽뇽")
									  .build();
		UserCellPhoneEntity2 phone = UserCellPhoneEntity2.builder()
														 .phoneNumber("010-8954-8791")
														 .model("아이폰12")
														 .user(user)
														 .build();
		phoneRepo2.save(phone);
	}
	
	// 부 table을 이용해서 주 table에 insert
//	@Test
	void f2() {
		UserEntity2 user = UserEntity2.builder()
									  .userid("dark")
									  .username("뇽뇽")
									  .build();
		UserCellPhoneEntity2 phone = UserCellPhoneEntity2.builder()
														 .phoneNumber("010-8954-8791")
														 .model("아이폰12")
														 .user(user)
														 .build();
		phoneRepo2.save(phone);
	}
	
	// 주 table을 이용해서 부 table에 insert
//	@Test
	void f1() {
		UserCellPhoneEntity phone = UserCellPhoneEntity.builder()
													   .phoneNumber("010-5678-1234")
													   .model("아이폰15")
													   .build();
		phoneRepo.save(phone);
		
		UserEntity user = UserEntity.builder().userid("flower").username("무궁화").phone(phone).build();
		userRepo.save(user);
	}
}
