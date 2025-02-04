package com.shinhan.firstzone.manytoone;

import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CustomerRepository extends CrudRepository<CustomerEntity, String>,
	   PagingAndSortingRepository<CustomerEntity, String>,
	   QuerydslPredicateExecutor<CustomerEntity> {
	   /* QuerydslPredicateExecutor<T>
		  QDomain이 자동 셍성됨 → pom.xml이 plugin으로 등록되어있음 */

	// 1. 기본 제공되는 함수 ex)findAll(), findById(), save() 등
	
}
