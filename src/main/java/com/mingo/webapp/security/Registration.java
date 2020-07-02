package com.mingo.webapp.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.mingo.webapp.model.User;


public class Registration {
	
	
	private String password;
	private String name;
	private String surname;
	private String username;
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	
	public User toUser(PasswordEncoder passEncoder) 
	{
		User user = new User(name, surname, username,  passEncoder.encode(password), email);
		
		
		return user;
	}
	
	@Override
	public String toString() 
	{
		return String.format("Name: %s\tUsername: %s\tPassword: %s\tEmail: %s", name, username, password, email);
	}

}
