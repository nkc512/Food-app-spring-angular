package com.example.demo.model;

import com.example.demo.model.CartProduct;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart")
public class Cart {
    @Transient
    public static final String SEQUENCE_NAME = "cart_sequence";
    @Id
    private String id;
	private Date dateCreated;
	private String status;
	private Float totalCost;
	private List<CartProduct> cartItems;
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
	
	public List<CartProduct> getCartItems() {
		return cartItems;
	}
	public void setCartItems(List<CartProduct> cartItems) {
		this.cartItems = cartItems;
	}
	public Cart() {
		super();
	}
	public Cart(String id, Date dateCreated, String status, Float totalCost, List<CartProduct> cartItems) {
		super();
		this.id = id;
		this.dateCreated = dateCreated;
		this.status = status;
		this.totalCost = totalCost;
		this.cartItems = cartItems;
	}
	@Override
	public String toString() {
		return "Cart [id=" + id + ", dateCreated=" + dateCreated + ", status=" + status + ", totalCost=" + totalCost
				+ ", cartItems=" + cartItems + "]";
	}
	
	
}
