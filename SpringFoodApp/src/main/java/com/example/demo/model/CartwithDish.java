package com.example.demo.model;

import java.util.Date;
import java.util.List;

public class CartwithDish {
    private String id;
	private Date dateCreated;
	private String status;
	private Float totalCost;
	private List<CartItem> cartItems;
	public CartwithDish(String id, Date dateCreated, String status, Float totalCost, List<CartItem> cartItems) {
		super();
		this.id = id;
		this.dateCreated = dateCreated;
		this.status = status;
		this.totalCost = totalCost;
		this.cartItems = cartItems;
	}
	public CartwithDish() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Float getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(Float totalCost) {
		this.totalCost = totalCost;
	}
	public List<CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	@Override
	public String toString() {
		return "CartwithDish [id=" + id + ", dateCreated=" + dateCreated + ", status=" + status + ", totalCost="
				+ totalCost + ", cartItems=" + cartItems + "]";
	}
	

}