package com.personal.diccionariobd.modelodatos.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final Logger log = LoggerFactory.getLogger(WebSecurityConfiguration.class);
	
	   @Autowired
       private UserDetailsServiceImpl myUserDetailService;
	   
	   @Autowired
	   private BCryptPasswordEncoder prueba;
	   
	   
	
	 @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	       // auth.authenticationProvider(daoAuthenticationProvider());
		 auth.userDetailsService(myUserDetailService);
		 
		 log.info("Metodo de Configure Globalsss");
		 
/*
	        auth
	        .inMemoryAuthentication()
	        .withUser("user").password(passwordEncoder().encode("password")).roles("ADMINISTRADOR")
	        .and()
	        .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMINISTRADOR")
	        .and()
	        .withUser("joe").password(passwordEncoder().encode("123456")).roles("ADMINISTRADOR");*/
	        
	     

	    }
	 
/*	    @Bean
	    public BCryptPasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }*/
	    
/*	    @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }*/
	 
	    @Override
	    protected void configure(HttpSecurity http) throws Exception {
	    	log.info("Metodo de Configure HttpSecurity: " + prueba);
	    	
	        http.csrf().disable();

	        http
	                .authorizeRequests()
	                .antMatchers("/js/**").permitAll()
	                .antMatchers("/css/**").permitAll()
	                .antMatchers("/img/**").permitAll()
	                .antMatchers("/fonts/**").permitAll()
	                .antMatchers("/forgot_password/**").permitAll()
	                .antMatchers("/reset_password/**").permitAll()
	                .antMatchers("/clientes/**").hasAnyRole("ADMINISTRADOR")
	                //.antMatchers("/clientes/**").permitAll()
	                .antMatchers("/","/home").authenticated()
	                .anyRequest().authenticated()
	                .and()
	                .formLogin()
	                .loginPage("/login")
	    .failureUrl("/login-error")
	                .permitAll()
	                .and()
	                .logout()
	                .permitAll();
	    }
	    

	    
}
