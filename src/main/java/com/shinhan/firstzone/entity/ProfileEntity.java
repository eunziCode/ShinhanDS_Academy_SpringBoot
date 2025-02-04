package com.shinhan.firstzone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter@ToString
@EqualsAndHashCode(of = {"fno"})
@Entity
@Table(name = "tbl_profile")
public class ProfileEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto increment됨 
	Long fno;
	
	String fname;
	
	boolean currentYn; //DB의 칼럼이름은 current_yn
	
	@ManyToOne
	MemberEntity member;  //DB의 칼럼이름은 member_mid
}
/*
alter table if exists tbl_profile 
add constraint FKtn8ouaarhmqxf6bqp9cnjkqrx 
foreign key (member_mid) 
references tbl_members (mid)
*/
