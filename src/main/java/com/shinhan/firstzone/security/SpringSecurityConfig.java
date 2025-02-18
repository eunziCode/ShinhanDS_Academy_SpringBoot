package com.shinhan.firstzone.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

//SpringBoot 3버전 사용중
// @Configuration, @EnableWebSecurity, @Bean : application 시작시 해석함
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	private static final String[] MANAGER_LIST = {"/security/manager"};
	private static final String[] ADMIN_LIST = {"/security/admin"};
	private static final String[] USER_LIST = {"/security/user", "/webboard/*"};
	private static final String[] WHITE_LIST = {"/security/all", "/emp/**","/api/webboard/**", "/replies/**",
												"/auth/*", "/v3/**", "/swagger-ui/**"};
	/// 정적자원 : "/assets/*", "/css/*", "/js/*", "/vendor/**" 
	
	@Bean
	public SecurityFilterChain filterChain2(HttpSecurity http) throws Exception {
		// 조회는 token 없어도 됨, 입력&수정&삭제 token 필요
		http.csrf(c->c.disable()); // default는 csrf가 enabled로 되어있음
		http.authorizeHttpRequests(auth->{
			// 권한이 필요한 페이지들 설정
			auth.requestMatchers(MANAGER_LIST).hasRole("MANAGER");
			auth.requestMatchers(ADMIN_LIST).hasRole("ADMIN");
			auth.requestMatchers(USER_LIST).hasRole("USER");
			
			// 특정 URL들 허용
			auth.requestMatchers(WHITE_LIST).permitAll();
			
			// 위에 선언되지 않은 권한은 로그인이 되기만 하면 인가
			auth.anyRequest().authenticated(); 
		});
		
		/* react 공부시 로그인 없이 진행하기 위해 주석 처리(2025.02.18)
		http.formLogin(login->{
			login.loginPage("/auth/login") // 로그인 요청 주소
				 .usernameParameter("mid") // username을 mid로 받겠다는 의미
				 .defaultSuccessUrl("/auth/loginSuccess") // 로그인 성공시 요청 주소
				 .permitAll(); // 권한 허용
		});
		
		http.logout(out->{
			out.logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
			   .logoutSuccessUrl("/auth/login")
			   .invalidateHttpSession(true);
		});
		
		http.exceptionHandling(handling->{
			handling.accessDeniedPage("/auth/accessDenined");
		});
		*/
		
		return http.build();
	}
	
}
