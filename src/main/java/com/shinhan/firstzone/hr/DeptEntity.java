package com.shinhan.firstzone.hr;

import java.util.List;

import org.hibernate.annotations.BatchSize;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "emplist")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_departments")
public class DeptEntity {
	@Id
	Long department_id;
	String department_name;
	
	@OneToOne
	@JoinColumn(name = "manager_id")
	EmpEntity manager; // 직원 중 1명이 매니저
	
	@BatchSize(size = 100)
	@JsonIgnore
	@OneToMany(mappedBy = "dept", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	List<EmpEntity> emplist;
}
