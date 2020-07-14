package com.example.demo.model;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cartitem")
public class CartItem {
	private Dish dish;
	private Long quantity;
	public CartItem(Dish dish, Long quantity) {
		super();
		this.dish = dish;
		this.quantity = quantity;
	}
	
	public CartItem(CartItem cartItem) {
		super();
	}

	public Dish getDish() {
		return dish;
	}
	public void setDish(Dish dish) {
		this.dish = dish;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "CartItem [dish=" + dish + ", quantity=" + quantity + "]";
	}

	public CartItem() {
		super();
	}
	
}
