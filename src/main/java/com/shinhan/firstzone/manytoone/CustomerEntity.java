package com.shinhan.firstzone.manytoone;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity // table DDL이 생성됨(create table CustomerEntity ~~~)
@Table(name = "tbl_customer")
public class CustomerEntity {

	@Id // Primary Key임을 의미 → 없으면 오류!
	private String customerId; // DB에 생성되는 컬럼명 : customer_id
	private String customerName; // DB에 생성되는 컬럼명 : customer_name
	private String customerphone; // DB에 생성되는 컬럼명 : customer_phone
	
}
