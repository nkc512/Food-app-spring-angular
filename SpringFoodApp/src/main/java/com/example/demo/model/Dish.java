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
	
	@NotBlank
	private String cafeterianame;
	
    @NotBlank
	private String dishname;
	
	private int price;

    @NotBlank
	private String category;
	
    @NotBlank
	private String vegNonveg;
	
	

	private String availability;
	
	@NotBlank
	@Size(max=100)
	private String description;

	public Dish() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCafeterianame() {
		return cafeterianame;
	}

	public void setCafeterianame(String cafeterianame) {
		this.cafeterianame = cafeterianame;
	}

	public String getDishname() {
		return dishname;
	}

	public void setDishname(String dishname) {
		this.dishname = dishname;
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
		return vegNonveg;
	}

	public void setVegNonveg(String vegNonveg) {
		this.vegNonveg = vegNonveg;
	}

	public String getAvailability() {
		return availability;
	}

	public void setAvailability(String availability) {
		this.availability = availability;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Dish(Long id, @NotBlank String cafeterianame, @NotBlank String dishname, int price,
			@NotBlank String category, @NotBlank String vegNonveg, String availability,
			@NotBlank @Size(max = 100) String description) {
		super();
		this.id = id;
		this.cafeterianame = cafeterianame;
		this.dishname = dishname;
		this.price = price;
		this.category = category;
		this.vegNonveg = vegNonveg;
		this.availability = availability;
		this.description = description;
	}

}