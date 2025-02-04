package com.shinhan.firstzone.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
@EqualsAndHashCode(of = {"mid"})
@Entity
@Table(name = "tbl_members")
public class MemberEntity {
    @Id //PK를 의미한다. 
	String mid;
	String mpassword;
	String mname;
	@Enumerated(EnumType.STRING)
	MemberRole mrole; 
	
}
