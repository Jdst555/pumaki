package com.mingo.webapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

//@Entity
public class Product {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private String name;
	private String unit;
	private float price;
	private String img;
	
	public void setId(Long id) {this.id = id;}
	public void setName(String name) {this.name = name;}
	public void setUnit(String unit) {this.unit = unit;}
	public void setPrice(float price) {this.price = price;}
	public void setImg(String img) {this.img = img;}
	
	public Long getId() {return id;}
	public String getName() {return name;}
	public String getUnit() {return unit;}
	public float getPrice() {return price;}
	public String getImg() {return img;}
	
	@Override
	public String toString() 
	{
		return String.format("id: %d. name: %s. unit: %s. price: %f", id, name, unit, price);
	}

}
