package com.shinhan.firstzone;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.entity.MemberRole;
import com.shinhan.firstzone.entity.ProfileEntity;
import com.shinhan.firstzone.entity.QProfileEntity;
import com.shinhan.firstzone.repository.MemberRepository;
import com.shinhan.firstzone.repository.ProfileRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ManyToOneTestMember {

	@Autowired
	MemberRepository memberRepo;
	
	@Autowired
	ProfileRepository profileRepo;
	
	
	//[Quiz1] profile의 fno=7인 data 조회
	//Primary key로 조회함으로 default 제공함수를 사용한다. 
	//@Test
	public void quiz1() {
		profileRepo.findById(7L).ifPresentOrElse(pro->{
			log.info(pro.toString());
		}, ()->{log.info("data가 존재하지않음");});
	}
	//[Quiz2] profile의 fname like '%face%' 인 data조회
	//규칙에 맞는 함수를 추가한다.
	//@Test
	public void quiz2() {
		profileRepo.findByFnameContaining("face").forEach(pro->{
			log.info(pro.toString());
		}); 
	}
	
	//[Quiz3] profile의 currenYN이 true인 data조회
	//@Test
	public void quiz3() {
		profileRepo.findByCurrentYnTrue().forEach(pro->{
			log.info(pro.toString());
		}); 
	}
	//@Test
	public void quiz3_2() {
		profileRepo.findByCurrentYn(true).forEach(pro->{
			log.info(pro.toString());
		}); 
	}
	//[Quiz4] 특정member 'spring9'의 profile data조회
	//@Test
	public void quiz4() {
		MemberEntity member = MemberEntity.builder().mid("spring9").build();
		profileRepo.findByMember(member).forEach(pro->{
			log.info(pro.toString());
			System.out.println("파일이름:" + pro.getFname());
			System.out.println("현재profile여부:" + pro.isCurrentYn());
			System.out.println("이름:" + pro.getMember().getMname() );
		}); 
	}
	//****규칙에 맞는 함수이름정의
	//[Quiz5] 특정member 'spring9'의 currenYN이 true인 profile data조회
	//@Test
	public void quiz5() {
		MemberEntity member = MemberEntity.builder().mid("spring9").build();
		
		MemberEntity member2 = new MemberEntity();
		member2.setMid("zzilre9");
		
		MemberEntity member3 = new MemberEntity("spring9", null, null, null);
		
		profileRepo.findByMemberAndCurrentYn(member, false).forEach(pro->{
			log.info(pro.toString());
			System.out.println("파일이름:" + pro.getFname());
			System.out.println("현재profile여부:" + pro.isCurrentYn());
			System.out.println("이름:" + pro.getMember().getMname() );
		}); 
	}
	//****JPQL
	//[Quiz-6]
	//@Test
	public void quiz6() {
		MemberEntity member = MemberEntity.builder().mid("spring9").build();		
		profileRepo.quiz6(member, false).forEach(pro->{
			log.info(pro.toString());
		}); 
		log.info("----nativeQuery----------");
		profileRepo.quiz6_1(member.getMid(), true).forEach(pro->{
			log.info(pro.toString());
		}); 		
	}
	
	//****Querydsl : JAVA로 SQL문 작성 
	//[Quiz-7]
	//@Test
	public void quiz7() {
		MemberEntity member = MemberEntity.builder().mid("spring9").build();		
		QProfileEntity profile = QProfileEntity.profileEntity;
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(profile.member.eq(member));
		builder.and(profile.currentYn.eq(false));
		profileRepo.findAll(builder).forEach(pro->{
			log.info(pro.toString());
		}); 		
	}
	

	//Member를 통해서 profile에 접근하기...member가 몇개의 profile을 가지는지? 
	//@Test
	public void getMember() {
		memberRepo.getMemberWithProfileCount("ing").forEach(arr->log.info(Arrays.toString(arr)));
	}
	
	
	
	
	//1번 10번 2명의 profile을 등록
	//@Test
	public void insertProfile() {
		MemberEntity member1 = MemberEntity.builder().mid("spring7").build();
		MemberEntity member9 = memberRepo.findById("spring8").orElse(null);
		
		IntStream.rangeClosed(11, 13).forEach(i->{
			ProfileEntity profile = ProfileEntity.builder()
					.fname("foot-"+i+".jpg")
					.currentYn(i==13?true:false)
					.member(member1)
					.build();
			profileRepo.save(profile);
		});
		
		IntStream.rangeClosed(20, 23).forEach(i->{
			ProfileEntity profile = ProfileEntity.builder()
					.fname("cup-"+i+".jpg")
					.currentYn(i==23?true:false)
					.member(member9)
					.build();
			profileRepo.save(profile);
		});
		
		
		
	}
	
	//@Test
	public void memberInsert() {
		IntStream.rangeClosed(1, 10).forEach(i->{
			MemberEntity member = MemberEntity.builder()
					.mid("spring" + i)
					.mpassword("1234")
					.mname("은지"+(11-i))
					.mrole(i%5==0?MemberRole.MANAGER:i==9?MemberRole.ADMIN:MemberRole.USER )  //manager(2),admin(1),user(7)
					.build();
			memberRepo.save(member);
		});
	}
	
	@Autowired
	PasswordEncoder passEncoder;
	
	@Test
	public void memberUpdate() {
		memberRepo.findById("spring10").ifPresent(m->{
			m.setMpassword(passEncoder.encode("1234"));
			m.setMrole(MemberRole.USER);
			memberRepo.save(m);
		});
	}
	
//	@Test
	public void memberInsert2() {
		MemberEntity member = MemberEntity.builder()
										  .mid("manager1")
										  .mpassword(passEncoder.encode("1234"))
										  .mname("매니저1")
										  .mrole(MemberRole.MANAGER)
										  .build();
		memberRepo.save(member);
	}
}





