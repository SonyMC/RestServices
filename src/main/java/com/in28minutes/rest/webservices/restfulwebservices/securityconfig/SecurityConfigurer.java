//package com.in28minutes.rest.webservices.restfulwebservices.securityconfig;
//
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
//	
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//	    http.authorizeRequests()
//	        .antMatchers("/h2-console/**").permitAll()
//	        .anyRequest().authenticated();
//	    http.headers().frameOptions().sameOrigin();
//	}
//
//}
