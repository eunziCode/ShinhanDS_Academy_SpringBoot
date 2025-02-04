package com.shinhan.firstzone.onetomany;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_pdsfile")
public class PDSFileEntity {
	@Id // 필수
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	// IDENTITY:auto increment 추가, auto: sequence생성
	private Long fno;
	private String fileName;
	
}
