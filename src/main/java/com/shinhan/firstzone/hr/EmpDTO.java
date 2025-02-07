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
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmpDTO {
	Long employee_id;
	String first_name;
	String last_name;
	String email;
	String phone_number;
	Date hire_date;
	
	String job_id;
	String job_title;
	
	Long salary;
	Double commission_pct;
	
	Long manager_id;
	
	Long department_id;
	String department_name;
}
