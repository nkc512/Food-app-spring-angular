package com.example.demo.repository;
import com.example.demo.model.Order;

import java.util.ArrayList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

	@Query (value = "{'userName' : ?0 }")
	ArrayList<Order> findByUsername(String userName);

	@Query (value = "{ $and: [ { 'status' : 'Placed' }, { 'restaurantName' : ?0 } ] }")
	ArrayList<Order> restaurantWisePlacedOrders( String restaurantName);
	
	@Query (value = "{ $and: [ { 'status' : 'Accepted' }, { 'restaurantName' : ?0 } ] }")
	ArrayList<Order> restaurantWiseAcceptedOrders( String restaurantName);
	
	@Query (value = "{ $and: [ { 'status' : 'Cooking' }, { 'restaurantName' : ?0 } ] }")
	ArrayList<Order> restaurantWiseCookingOrders( String restaurantName);
	
	@Query (value = "{ $and: [ { 'status' : 'Ready' }, { 'restaurantName' : ?0 } ] }")
	ArrayList<Order> restaurantWiseReadyOrders( String restaurantName);

	@Query (value = "{ $and: [ { 'order_id': ?0}, { 'userName' : ?1 } ] }")
	Order findByIdAndUsername(String id, String userName);
}
