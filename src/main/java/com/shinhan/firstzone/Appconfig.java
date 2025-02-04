package com.shinhan.firstzone;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration  //@SpringBootApplication에서 읽음 
public class Appconfig {

	Appconfig(){
		log.info("---------[@Configuration]Appconfig생성----------");
	}
	
	@Bean  //객체를 만든다. (==@component)
	public SpringComponent1 makeBean() {
		return new SpringComponent1();
	}
}
