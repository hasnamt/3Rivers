//package com.ibm.demo.config;
//
//import javax.naming.Context;
//import javax.naming.InitialContext;
//import javax.sql.DataSource;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Override
//	public void configure(AuthenticationManagerBuilder auth)
//			throws Exception {
//
//		// in-memory authentication
//		 auth.inMemoryAuthentication().withUser("root").password("root").roles("USER");
//	}
//
//	@Override
//    public void configure(WebSecurity web) throws Exception {
//        web
//            .ignoring()
//                // Spring Security should completely ignore URLs ending with .html
//                .antMatchers("/*.html");
//    }
//
//}
