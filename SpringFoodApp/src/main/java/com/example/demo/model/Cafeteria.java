package com.example.demo.model;

import javax.persistence.Id;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Document(collection = "cafeteria_manager")
public class Cafeteria  {

    @Transient
    public static final String SEQUENCE_NAME = "cafe_sequence";

	@Id
    private String id;

    @Size(max = 50)
    private String cafename;
    
    @DBRef
    private User cafeuser;
    
    @JsonManagedReference
    @DBRef
//    private List < Dish > dishes;
    
    private boolean accountactive;

    public Cafeteria() {

    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCafename() {
		return cafename;
	}

	public void setCafename(String cafename) {
		this.cafename = cafename;
	}

//	public List<Dish> getDishes() {
//		return dishes;
//	}
//
//	public void setDishes(List<Dish> dishes) {
//		this.dishes = dishes;
//	}

	public Cafeteria(String id, @Size(max = 50) String cafename) {
		super();
		this.id = id;
		this.cafename = cafename;
//		this.dishes = dishes;
	}

	public User getCafeuser() {
		return cafeuser;
	}

	public void setCafeuser(User cafeuser) {
		this.cafeuser = cafeuser;
	}

	public boolean isAccountactive() {
		return accountactive;
	}

	public void setAccountactive(boolean accountactive) {
		this.accountactive = accountactive;
	}

	public Cafeteria(String id) {
		super();
		this.id = id;
	}



}