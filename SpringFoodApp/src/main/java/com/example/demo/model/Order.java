package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Order")
public class Order {
	
	@Id
	private String order_id;
	
	@NotBlank
	private String restaurantName;
	
	@NotBlank
	private String userName;
	
	private int payableAmount;
	

	private List<CartItem> items;
	
	@NotBlank
	private String status;
	
	private  Date createdAt = new Date();
	
	
	
	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}



	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getPayableAmount() {
		return payableAmount;
	}

	public void setPayableAmount(int payableAmount) {
		this.payableAmount = payableAmount;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Order [order_id=" + order_id + ", restaurantName=" + restaurantName + ", userName=" + userName
				+ ", payableAmount=" + payableAmount + ", items=" + items + ", status=" + status + ", createdAt="
				+ createdAt + "]";
	}



    
}
