package com.example.demo.dish.repositories;

import com.example.demo.dish.models.Dish;

import java.awt.List;
import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DishRepository extends MongoRepository<Dish, String> {

	
	@Query (value = "{ $and: [ { 'dishName' : ?0 }, { 'restaurantName' : ?1 } ] }")
	ArrayList<Dish> dishAlreadyExist(String dishName, String restaurantName);
		
	
	
}