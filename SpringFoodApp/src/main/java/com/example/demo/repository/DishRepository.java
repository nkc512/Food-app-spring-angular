package com.example.demo.repository;

import java.awt.List;
import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Dish;

@Repository
public interface DishRepository extends MongoRepository<Dish, String> {

	
	@Query (value = "{ $and: [ { 'dishName' : ?0 }, { 'restaurantName' : ?1 } ] }")
	ArrayList<Dish> dishAlreadyExist(String dishName, String restaurantName);
	
	@Query (value = "{'restaurantName' : ?0 }")
	ArrayList<Dish> findRstaurantDishes(String restaurantname);
	
}