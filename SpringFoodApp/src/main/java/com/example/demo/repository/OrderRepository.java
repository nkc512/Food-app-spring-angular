package com.example.demo.repository;
import com.example.demo.model.Dish;
import com.example.demo.model.Order;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

	@Query (value = "{'user_id' : ?0 }")
	ArrayList<Order> findByUserid(String user_id);

}
