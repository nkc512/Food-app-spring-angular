package com.example.demo.model;

import java.util.List;

public class CartItem {
	private int qnty;
	private Dish dish;
	
	
	
	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getQnty() {
		return qnty;
	}
	public void setQnty(int qnty) {
		this.qnty = qnty;
	}
	public Dish getDish() {
		return dish;
	}
	public void setDish(Dish dish) {
		this.dish = dish;
	}
	
	
}
