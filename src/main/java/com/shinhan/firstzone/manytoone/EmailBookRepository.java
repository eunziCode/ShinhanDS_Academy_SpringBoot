package com.shinhan.firstzone.manytoone;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface EmailBookRepository extends CrudRepository<EmailBookEntity, Long>,
	   PagingAndSortingRepository<EmailBookEntity, Long>,
	   QuerydslPredicateExecutor<EmailBookEntity> {

	// 1. 기본 제공되는 함수 ex)findAll(), findById(), save() 등
	
	// 2. 규칙에 맞는 함수정의 추가
	List<EmailBookEntity> findByCust(CustomerEntity cust);
	
	// 3-1. JPQL : native query가 아닌 경우(자바 ▶ 자바의 entity, field)
	@Query("SELECT cust.customerName, COUNT(book) "
			+ "FROM  CustomerEntity cust LEFT outer JOIN EmailBookEntity book "
			+ "         ON(cust = book.cust) "
			+ "GROUP BY cust.customerName "
			+ "ORDER BY cust.customerName")
	List<Object[]> findByCustEmailCount();
	
	// 3-2. JPQL : native query인 경우(SQL ▶ DB의 컬럼으로 작성)
	@Query(value="SELECT cust.customer_name, COUNT(book.book_id) "
			+ "FROM  tbl_customer cust LEFT outer JOIN tbl_emailbook book "
			+ "         ON(cust.customer_id = book.cust_customer_id) "
			+ "GROUP BY cust.customer_name "
			+ "ORDER BY cust.customer_name", nativeQuery = true)
	List<Object[]> findByCustEmailCount2();
}
