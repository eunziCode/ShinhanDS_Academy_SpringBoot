package com.shinhan.firstzone;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.firstzone.manytoone.CustomerEntity;
import com.shinhan.firstzone.manytoone.CustomerRepository;
import com.shinhan.firstzone.manytoone.EmailBookEntity;
import com.shinhan.firstzone.manytoone.EmailBookRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ManyToOneTest2 {

	@Autowired
	CustomerRepository custRepo;
	
	@Autowired
	EmailBookRepository emailRepo;
	
	// [Q3] 고객별 이메일의 개수를 조회
	// cust001-0개, cust002-3게, cust003-0개, cust004-2개, cust005-0개
	@Test
	public void q3() {
		System.out.println("-----JPQL native query가 아닌 경우-----");
		emailRepo.findByCustEmailCount().forEach(arr->{
			log.info(Arrays.toString(arr));
		});
		System.out.println("-----JPQL native query인 경우-----");
		emailRepo.findByCustEmailCount2().forEach(arr->{
			log.info(Arrays.toString(arr));
		});
		
	}
	
	// [Q2] customerId="cust004"인 이메일 정보조회
	//@Test
	public void q2() {
		String customerId="cust002";
		CustomerEntity cust = CustomerEntity.builder().customerId(customerId).build();
		
		emailRepo.findByCust(cust).forEach(entity->{
			log.info(entity.toString());
		});
	}
	
	// [Q1] book_id가 1인 이메일 정보 조회
	//@Test
	public void q1() {
		Long book_id = 6L;
		emailRepo.findById(book_id).ifPresentOrElse(entity->{
			log.info("데이터존재 ▶ " + entity.toString());
		}, ()->{
			log.info("데이터 미존재");
		});
	}
	
	// emailbook data 조회
	//@Test
	public void selectEmail() {
		emailRepo.findAll().forEach(i->{
			log.info(i.toString());
			log.info(i.getEmail() + "▶" + i.getCust().getCustomerName());
		});
	}
	
	// 특정 고객의 이메일 insert
	//@Test
	public void emailInsert() {
		String customerId = "cust004";
		CustomerEntity cust = CustomerEntity.builder().customerId(customerId).build();
		
		EmailBookEntity[] arr = new EmailBookEntity[3];
		arr[0] = EmailBookEntity.builder().email("many@gmail.com").password("1234")
								.mainYn(true).cust(cust).build();
		
		arr[1] = EmailBookEntity.builder().email("one@gmail.com").password("1234")
								.mainYn(false).cust(cust).build();
		
//		arr[2] = EmailBookEntity.builder().email("bb@gmail.com").password("1234")
//								.mainYn(false).cust(cust).build();
		for(EmailBookEntity email:arr) {
			emailRepo.save(email);
		}
	}
	
	// 고객 조회
	//@Test
	public void selectAll() {
		custRepo.findAll().forEach(cust->{
			log.info(cust.toString());
		});
	}
	
	// 고객 5명 insert
	//@Test
	public void custInsert() {
		IntStream.rangeClosed(1, 5).forEach(i->{
			CustomerEntity cust = CustomerEntity.builder()
												.customerId("고객"+i)
												.customerName("스프링"+i)
												.customerphone("010-1234-567"+i)
												.build();
			custRepo.save(cust);
		});
	}
}
