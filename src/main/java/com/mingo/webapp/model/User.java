package com.mingo.webapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//clase que representa un usuario

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String surname;
	private String password;
	private String email;
	//setters
	public void setId(Long id) {this.id = id;}
	public void setName(String name) {this.name = name;}
	public void setsurename(String surename) {this.surname = surename;}

	public void setPassword(String password) {this.password = password;}
	public void setemail(String email) {this.email = email;}
	//getters
	public Long getId() {return id;}
	public String getName() {return name;}
	public String getSurname() {return surname;}

	public String getPassword() {return password;}
	public String getEmail() {return email;}
	
	@Override
	public String toString() 
	{
		
		return String.format("id: %d. name: %s. surename: %s. password: $s. email: %s", id, name, surname, password, email); 
	}

	

}
