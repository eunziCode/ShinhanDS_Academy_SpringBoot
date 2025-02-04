package com.shinhan.firstzone.manytoone;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity // table DDL이 생성됨(create table EmailBookEntity ~~~)
@Table(name = "tbl_emailbook")
public class EmailBookEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
	private Long bookId;
	
	@Column(length = 100, nullable = false)
	private String email;
	private String password;
	private boolean mainYn; // DB에 생성되는 컬럼명 : main_yn
	
	@ManyToOne
	CustomerEntity cust; // cust_customer_id
}
