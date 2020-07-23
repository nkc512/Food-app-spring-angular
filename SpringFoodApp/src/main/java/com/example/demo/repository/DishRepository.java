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
	@Query (value = "{ $and: [ { 'dishName' : ?0 }, { 'restaurantName' : ?1 } ] }")
	ArrayList<Dish> findBydishnamecontainingAndRestaurantName(String searchval,String RestaurantName);
	//@Query (value = "{ 'dishName': { '$in' : [ ?0 ] } }")
	//@Query (value = "{'dishName': { '$regex': '/.*searchval.*/' , '$options': 'i'}  }")
	@Query (value = "{'dishName': { '$regex': ?0 , '$options': 'i'}  }")
	ArrayList<Dish> findBydishnamecontaining(String searchval);
}
