package com.shinhan.firstzone.hr;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmpRepository extends JpaRepository<EmpEntity, Long> {

	// 1. 규칙에 맞는 함수 추가
	List<EmpEntity> findByJob(JobEntity job);
	List<EmpEntity> findByJobIsNull();
	List<EmpEntity> findByManagerIsNull();
//	List<EmpEntity> findByHireDateIsNull();
	
	// 3. JPQL 작성
	@Query("select emp from #{#entityName} emp where emp.hire_date is null")
	List<EmpEntity> findByHireDateIsNull2();
}
