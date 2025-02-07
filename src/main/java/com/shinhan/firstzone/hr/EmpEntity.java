package com.shinhan.firstzone.hr;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = {"job", "manager", "dept"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_employees")
public class EmpEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long employee_id;
	String first_name;
	String last_name;
	String email;
	String phone_number;
	Date hire_date;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "job_id")
	JobEntity job;
	
	Long salary;
	Double commission_pct;
	
	// fetch는 조회와 연관
	// cascade는 DML 연관 → 나의 entity의 변화가 연관 관계 entity 영향
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	EmpEntity manager;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "department_id")
	DeptEntity dept;
}
