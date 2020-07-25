package com.mingo.webapp.model;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import org.springframework.stereotype.Component;

@Component
public class Order {
	
	private Long id;
	private Date date;
	private String address;
	private boolean active;
	private float total;
	private ArrayList<Item> itemList = new ArrayList<>();
	
	
	//setters
	public void setId(Long id) {this.id = id;}
	public void setDate(Date date) {this.date = date;}
	public void setAddress(String address) {this.address = address;}
	public void setActive(boolean active) {this.active = active;}
	public void setTotal(float total) {this.total =  total;}
	public void setItemList(ArrayList<Item> itemList) {
		this.itemList = itemList;
	}
	
	
	//getters
	public Long getId() {return id;}
	public Date getDate() {return date;}
	public String getAddress() {return address;}
	public boolean getActive() {return active;}
	public float getTotal() {return total;}
	public ArrayList<Item> getItemList() {
		return itemList;
	}
	
	
	
	
	@Override
	public String toString()
	{
		return String.format("id: %d. date: %s. address: %s. active: %s. total: %s", id, date, address, active, total);
	}
	
}
