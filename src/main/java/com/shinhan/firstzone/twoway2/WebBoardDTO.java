package com.shinhan.firstzone.twoway2;

import java.sql.Timestamp;
import java.util.List;

import com.shinhan.firstzone.entity.MemberEntity;

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
public class WebBoardDTO {
	private Long bno;
	private String title;
	private String mid; 
	private String mname; 
	private String content;	
	private Timestamp regdate; 
	private Timestamp updatedate;
	private int replyCount;

}








