package com.example.demo.model;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="dish")
public class Dish {
	@Transient 
	public static final String SEQUENCE_NAME="dish_seq";
	
	
	@Id
	private Long id;
	
	private Cafeteria cafeteria;

	@NotBlank
	private String restaurantName;
	
    @NotBlank
	private String dishname;
	
	public Dish(Long id, Cafeteria cafeteria, @NotBlank String restaurantName, @NotBlank String dishName, int price,
			@NotBlank String category, @NotBlank String vegNonVeg, boolean availability,
			@NotBlank @Size(max = 100) String description) {
		super();
		this.id = id;
		this.cafeteria = cafeteria;
		this.restaurantName = restaurantName;
		this.dishname = dishName;
		this.price = price;
		this.category = category;
		this.vegNonVeg = vegNonVeg;
		this.availability = availability;
		this.description = description;
	}

	public Cafeteria getCafeteria() {
		return cafeteria;
	}

	public void setCafeteria(Cafeteria cafeteria) {
		this.cafeteria = cafeteria;
	}

	private int price;

    @NotBlank
	private String category;
	
    @NotBlank
	private String vegNonVeg;
	
	private boolean availability;
	
	@NotBlank
	@Size(max=100)
	private String description;

	public Dish() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Dish(String restaurantName, String dishName, int price, String category, String vegNonveg,
			boolean availability, @Size(max = 100) String description) {
		super();
		this.restaurantName = restaurantName;
		this.dishname = dishName;
		this.price = price;
		this.category = category;
		this.vegNonVeg = vegNonveg;
		this.availability = availability;
		this.description = description;
	}
	
	public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getDishName() {
		return dishname;
	}

	public void setDishName(String dishName) {
		this.dishname = dishName;
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

	public String getVegNonveg() {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVegNonVeg() {
		return vegNonVeg;
	}

}