package com.mingo.webapp.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	DataSource dataSource;
	PasswordEncoder passEncoder = (PasswordEncoder)new BCryptPasswordEncoder();
	

	protected void configure(AuthenticationManagerBuilder amb) throws Exception
	{
		
		amb.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery("select username, password, enabled from user " +
				"where username = ?").authoritiesByUsernameQuery("select username, authority "+
						"from authorities " +
						"where username = ?").passwordEncoder(new BCryptPasswordEncoder());
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests().antMatchers("/products", "/orders", "/nuevo_producto", "/registrar_nuevo_producto", "/new_o", "/ord_n", "/users").hasRole("ADMIN")
		.antMatchers("/shop", "/checkout", "/buy").hasAnyRole("USER", "ADMIN")
		.antMatchers("/", "/**").permitAll().and().formLogin().loginPage("/login").and().logout().logoutSuccessUrl("/");
		http.csrf().disable();
	}

}
