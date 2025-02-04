package com.shinhan.firstzone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//VO(Value Object) : 여러개의 값의 저장 묶음 

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter@Setter@ToString
public class CarVO {
	String model;
	int price;
	String color;
}
