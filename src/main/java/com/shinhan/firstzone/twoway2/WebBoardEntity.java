package com.shinhan.firstzone.twoway2;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shinhan.firstzone.entity.MemberEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "replies") // toString()메서드 생성시 제외시키기
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity // JPA 관리 대상 → @Table 생략시 @Entity이름이 table이름으로 사용
@Table(name = "tbl_webboards")
public class WebBoardEntity {
	@Id // PK, 필수
	// IDENTITY ▶ mariaDB의 컬럼에 auto increment 적용		oracle:sequence, mysql:identity
	// AUTO ▶ mariaDB에 sequence역할을 하는 table이 생성됨
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bno;
//    @NonNull //WebBoard가 build될때 반드시 setting해야한다. 
    @Column(nullable = false)  //DB칼럼이 not null
	private String title;
    
    @ManyToOne // 여러개의 게시글은 한명의 user가 작성함
	private MemberEntity writer; // writer_mid
	@Column(length = 100)
	private String content;	

	@CreationTimestamp
	private Timestamp regdate;//yyyy-MM-dd hh:mm:ss
	
	@UpdateTimestamp  //생성시 생성일자, 수정시 변경된다. 
	private Timestamp updatedate;

 
	@BatchSize(size = 100)
	@JsonIgnore
	@OneToMany(mappedBy = "board", 
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY )
	List<WebReplyEntity> replies;
	//@OneToMany와 @ManyToMany는 기본이 지연 로딩(LAZY)이다.
	//@ManyToOne이 EAGER임. 양방향이므로 reply에서 board정보필요하므로 N번 호출됨 
	//그러므로 OneToMany에서 지연로딩으로 변경하여도  N번 쿼리 호출된다. 
	//해결방안은 BatchSize조정 @BatchSize(size = 100)
 
}
