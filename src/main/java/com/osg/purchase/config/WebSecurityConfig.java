package com.osg.purchase.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.osg.purchase.service.MemberServiceImpl;

@Configuration

public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	   @Autowired
	    private MemberServiceImpl memberService;

	  @Override
	    protected void configure(HttpSecurity http) throws Exception {
	        http
	            .authorizeRequests()
	                .antMatchers("/").permitAll()
	                .antMatchers("/admin").hasAuthority("ROLE_ADMIN")
	                .anyRequest().authenticated()
	                .and()
	            .formLogin()
	                .loginPage("/login")
	                .permitAll()
	                .and()
	            .logout()
	                .permitAll();
	    }

	  @Bean
		public PasswordEncoder passwordEncoder(){
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			return encoder;
		}
	    @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	        auth
	            .userDetailsService(memberService)
	            .passwordEncoder(passwordEncoder());

	    }
}