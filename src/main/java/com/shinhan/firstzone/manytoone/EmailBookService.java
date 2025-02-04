package com.shinhan.firstzone.manytoone;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmailBookService {

	@Autowired
	CustomerRepository custRepo;
	
	@Autowired
	EmailBookRepository emailRepo;
	
	// [Q3] 고객별 이메일의 개수를 조회
	public List<ResponseEmailCountDTO> q3() {
		List<ResponseEmailCountDTO> datas = new ArrayList<>();
		emailRepo.findByCustEmailCount().forEach(arr->{
			ResponseEmailCountDTO dto = ResponseEmailCountDTO.builder()
															 .customerName((String)arr[0])
															 .count((Long)arr[1])
															 .build();
			datas.add(dto);
		});
		
		return datas;
	}
	
	// [Q2] customerId="cust004"인 이메일 정보조회
	public List<EmailBookEntity> q2(String customerId) {
		CustomerEntity cust = CustomerEntity.builder().customerId(customerId).build();
		
		return emailRepo.findByCust(cust);
	}
	
	// [Q1] 특정 book_id의 이메일 정보 조회
	public EmailBookEntity q1(Long book_id) {
		return emailRepo.findById(book_id).orElse(null);
	}
}
