package com.mingo.webapp.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Order {
	
	private Long id;
	private Date date;
	private String address;
	Map<Product, Integer> products = new HashMap<>();
	
	//setters
	public void setId(Long id) {this.id = id;}
	public void setDate(Date date) {this.date = date;}
	public void setAddress(String address) {this.address = address;}
	
	
	//getters
	public Long getId() {return id;}
	public Date getDate() {return date;}
	public String getAddress() {return address;}
	
	//lista
	public Integer addProduct(Product product, Integer number) 
	{
		return this.products.put(product, number);
	} 
	
	public boolean removeOneOfOne(Product product) 
	{
		if((this.products.get(product) == null ) || (this.products.get(product) == 0)) 
		{
			return false;
		}
		else 
		{
			int oldNumber = this.products.get(product);
			int newNumber = oldNumber - 1;
			this.products.remove(product);
			this.products.put(product, newNumber);
			return true;
		}
	}
	public boolean addOneOfOne(Product product) 
	{
		if(this.products.get(product) == null) 
		{
			this.products.put(product, 1);
			return true;
		}
		else if(this.products.get(product) >= 0) 
		{
			int oldNumber = this.products.get(product);
			int newNumber = oldNumber + 1;
			this.products.remove(product);
			this.products.put(product, newNumber);
			return true;
		}
		
		return false;
	}
	public void removeAllOfOne(Product product) 
	{
		this.products.remove(product);
	}
	
}