package com.shinhan.firstzone;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SpringComponent1 {

	SpringComponent1(){
		log.info("-----SpringComponent1생성자------");
	}
	public String getData() {
		return "SpringComponent1의 메서드 getData()!!!";
	}
}
