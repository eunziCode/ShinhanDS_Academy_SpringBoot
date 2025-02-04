package com.shinhan.firstzone.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.shinhan.firstzone.dto.GuestBookDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter 
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity //JPA관리한다. 
@Table(name = "t_guestbook")
public class GuestBookEntity extends BaseEntity{
    //id(PK)가 없는 경우 : 오류메시지 has no identifier
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Auto increment, Oracle은 sequence이용 
	private Long gno;
	
	@Column(length = 100,  nullable = false)
	private String title;
	
	@Column(length = 1500,  nullable = false)
	private String content;
	
	@Column(length = 150,  nullable = false)
	private String writer;

	@Override
	public String toString() {
		return "GuestBook정보 [gno=" + gno + ", title=" + title + ", content=" + content + ", writer=" + writer
				+ ",등록일, 수정일 =" + super.toString() + "]";
	}
	
 	
}




