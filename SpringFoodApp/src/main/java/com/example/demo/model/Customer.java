package com.example.demo.model;

import javax.persistence.Id;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customer")
public class Customer {
	
    @Transient
    public static final String SEQUENCE_NAME = "customer_sequence";
	@Id
	private Long id;
	private String name;
	private Cart cart;
	
	@DBRef
	private User customeruser;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Cart getCart() {
		return cart;
	}
	public void setCart(Cart cart) {
		this.cart = cart;
	}
	public Customer(Long id, String name, Cart cart) {
		super();
		this.id = id;
		this.name = name;
		this.cart = cart;
	}
	public Customer() {
		super();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Customer(Long id, String name, Cart cart, User customeruser) {
		super();
		this.id = id;
		this.name = name;
		this.cart = cart;
		this.customeruser = customeruser;
	}
	public User getCustomerUser() {
		return customeruser;
	}
	public void setCustomeruser(User customeruser) {
		this.customeruser = customeruser;
	}
	public Customer(User customeruser) {
		super();
		this.customeruser = customeruser;
	}
	
	
	
}
