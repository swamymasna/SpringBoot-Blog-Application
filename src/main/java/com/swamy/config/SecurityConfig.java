package com.swamy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {		


	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//		http.csrf().disable()
//		.authorizeRequests((authorize) -> {
//			try {
//				authorize
//						.antMatchers(HttpMethod.GET, "/api/**").permitAll()
//						.antMatchers("/api/auth/**").permitAll()
//						.anyRequest()
//						.authenticated()
//						.and()
//						.httpBasic();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});

		
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
		.antMatchers("/api/auth/**").permitAll()
		.anyRequest()
		.authenticated();
		
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
	
		
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//
//		auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
//	}
//
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//
//		/*
//		http.csrf().disable().authorizeRequests()
//		.antMatchers(HttpMethod.GET, "/api/**").permitAll()
//		.antMatchers("/api/auth/**").permitAll()
//		.anyRequest()
//		.authenticated().and().httpBasic();
//		*/
//		
//		http.csrf().disable().authorizeRequests()
//		.antMatchers(HttpMethod.GET, "/api/**").permitAll()
//		.antMatchers("/api/auth/**").permitAll()
//		.anyRequest()
//		.authenticated().and().httpBasic();
//	}

//	@Override
//	@Bean
//	public AuthenticationManager authenticationManagerBean() throws Exception {
//		return super.authenticationManagerBean();
//	}
}
