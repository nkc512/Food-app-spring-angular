package com.example.demo.dish.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="dish")
public class Dish {
	@Id
	private String id;

	@NotBlank
	private String restaurantName;
	
    @NotBlank
	private String dishName;
	
	private int price;

    @NotBlank
	private String category;
	
    @NotBlank
	private String vegNonVeg;
    
    @NotBlank
	private String imgName;
	
	private boolean availability;
	
	@NotBlank
	@Size(max=100)
	private String description;

	public Dish() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Dish(String id,String restaurantName, String dishName, int price, String category, String vegNonVeg,
			boolean availability, @Size(max = 100) String description) {
		super();
		this.id=id;
		this.restaurantName = restaurantName;
		this.dishName = dishName;
		this.price = price;
		this.category = category;
		this.vegNonVeg = vegNonVeg;
		this.availability = availability;
		this.description = description;
	}
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgName() {
		return imgName;
	}

	public void setImgName(String imgName) {
		this.imgName = imgName;
	}

	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getDishName() {
		return dishName;
	}

	public void setDishName(String dishName) {
		this.dishName = dishName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getVegNonVeg() {
		return vegNonVeg;
	}

	public void setVegNonVeg(String vegNonVeg) {
		this.vegNonVeg = vegNonVeg;
	}

	public boolean isAvailability() {
		return availability;
	}

	public void setAvailability(boolean availability) {
		this.availability = availability;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
