package com.mingo.webapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//clase que representa un usuario cliente o admin

//@Entity
public class User {
	
	public User() 
	{
		super();
	}
	
	public User(String name, String surname, String username, String password, String email)
	{
		this.setName(name);
		this.setSurname(surname);
		this.setUsername(username);
		this.setPassword(password);
		this.setEmail(email);
		
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String surname;
	private String username;
	private String password;
	private String email;
	//setters
	public void setId(Long id) {this.id = id;}
	public void setName(String name) {this.name = name;}
	public void setSurname(String surname) {this.surname = surname;}
	public void setUsername(String username) {this.username = username;}
	public void setPassword(String password) {this.password = password;}
	public void setEmail(String email) {this.email = email;}
	//getters
	public Long getId() {return id;}
	public String getName() {return name;}
	public String getSurname() {return surname;}
	public String getUsername() {return username;}
	public String getPassword() {return password;}
	public String getEmail() {return email;}
	
	@Override
	public String toString() 
	{
		
		return String.format("id: %d. name: %s. surename: %s. username: %s password: %s. email: %s", id, name, surname, username, password, email); 
	}

	

}
