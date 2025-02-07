package com.shinhan.firstzone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import jakarta.persistence.EntityListeners;

@SpringBootApplication
@ComponentScan(basePackages = {"com.shinhan.firstzone","net.shinhan"})
//@EnableJpaRepositories(basePackages = {"com.shinhan.firstzone","net.shinhan"})
@EnableJpaAuditing  //@EntityListeners
@EnableAspectJAutoProxy
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
