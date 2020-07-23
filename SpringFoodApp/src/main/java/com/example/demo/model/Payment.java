package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.CreditCardNumber;


@Entity
@Table(name="payment")
public class Payment {
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="order_id",length=50)
	private String orderId;
	
	@Column(name="username",length=20)
	private String username;
	
	@Column(name="amount")
	private int amount;
	
	@Column(name="",length=20)
	private String restaurantName;
    //@CreditCardNumber
	
	@Column(name="card_number",length=20)
	private String cardNumber;
	
	@Column(name="expiration_date",length=20)
	private String expirationDate;
	
	@Column(name="card_owner",length=20)
	private String cardOwner;
	
	@Column(name="cvv",length=5)
	private int cvv;

	@Column(name="paid_at")
	private  Date paidAt = new Date();
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCardOwner() {
		return cardOwner;
	}

	public void setCardOwner(String cardOwner) {
		this.cardOwner = cardOwner;
	}

	public int getCvv() {
		return cvv;
	}

	public void setCvv(int cvv) {
		this.cvv = cvv;
	}


	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public Date getPaidAt() {
		return paidAt;
	}

	public void setPaidAt(Date paidAt) {
		this.paidAt = paidAt;
	}

	public Payment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Payment(int id, String orderId, String username, int amount, String restaurantName, String cardNumber,
			String expirationDate, String cardOwner, int cvv, Date paidAt) {
		super();
		this.id = id;
		this.orderId = orderId;
		this.username = username;
		this.amount = amount;
		this.restaurantName = restaurantName;
		this.cardNumber = cardNumber;
		this.expirationDate = expirationDate;
		this.cardOwner = cardOwner;
		this.cvv = cvv;
		this.paidAt = paidAt;
	}

	@Override
	public String toString() {
		return "Payment [id=" + id + ", orderId=" + orderId + ", username=" + username + ", amount=" + amount
				+ ", restaurantName=" + restaurantName + ", cardNumber=" + cardNumber + ", expirationDate="
				+ expirationDate + ", cardOwner=" + cardOwner + ", cvv=" + cvv + ", paidAt=" + paidAt + "]";
	}




}
