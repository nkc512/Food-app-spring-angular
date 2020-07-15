package com.example.demo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cartproduct")
public class CartProduct {
	private String id;
	private Long quantity;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public CartProduct(String id, Long quantity) {
		super();
		this.id = id;
		this.quantity = quantity;
	}
	@Override
	public String toString() {
		return "CartProduct [id=" + id + ", quantity=" + quantity + "]";
	}
	public CartProduct() {
		super();
	}
	

}
