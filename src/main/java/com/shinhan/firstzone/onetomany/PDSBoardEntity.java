package com.shinhan.firstzone.onetomany;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
@Table(name = "tbl_pdsboard")
public class PDSBoardEntity {
	@Id // 필수
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	// IDENTITY:auto increment 추가, auto: sequence생성
	private Long pid;
	private String pname;
	private String pwriter;
	
	// cascade는 부모의 DML이 자식에 영향을 줌
	// fetch는 부모 조회시 자식이 조회됨(=Eager)
	// fetch는 부모 조회시 자식은 조회되지 않음(=Lazy)
	// LAZY 사용한 경우 : toString에 제외되어있지 않으면 오류
	// LAZY 사용한 경우 : getFiles() 사용 오류
	// LAZY 사용한 경우 : getFiles() 사용가능하게 하려면 @Transactional 사용
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "pdsno") // tbl_pdsfile에 컬럼이 생성됨
	List<PDSFileEntity> files;
}
// 하나의 board는 여러개의 file을 가짐
/*
joincolumn의 설정이 없으면 중간 table이 생김
create table tbl_pdsboard_files (
pdsboard_pid bigint not null,
files_fno bigint not null
) engine=InnoDB
 */
