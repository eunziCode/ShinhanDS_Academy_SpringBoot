package com.shinhan.firstzone.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.shinhan.firstzone.entity.MemberEntity;
import com.shinhan.firstzone.entity.ProfileEntity;

public interface ProfileRepository 
   extends JpaRepository<ProfileEntity, Long>,
           QuerydslPredicateExecutor<ProfileEntity>{

	//규칙에 맞는 함수들을 정의한다. 
	List<ProfileEntity> findByFnameContaining(String fname);
	List<ProfileEntity> findByCurrentYnTrue();
	List<ProfileEntity> findByCurrentYn(boolean yn);
	List<ProfileEntity> findByMember(MemberEntity member);
	//[Quiz5] 규칙을 가진 함수이름 사용하기 
	List<ProfileEntity> findByMemberAndCurrentYn(MemberEntity member, boolean yn);
	
    //[Quiz6]JPQL...JAVA의 class이름 , field이름 사용 
	@Query("select profile "
			+ " from #{#entityName} profile "
			+ " where member = :mm "
			+ " and currentYn = :yn")
	List<ProfileEntity> quiz6(@Param("mm") MemberEntity member,
			                  @Param("yn") boolean yn);
	
	//[Quiz-6]JPQL naviveQuery ....DB의 table이름, 칼럼이름 
	@Query(value =  "select * "
			+ " from tbl_profile profile "
			+ " where member_mid = :mm "
			+ " and current_yn = :yn", nativeQuery = true)
	List<ProfileEntity> quiz6_1(@Param("mm") String mid,
			                  @Param("yn") boolean yn);

}
