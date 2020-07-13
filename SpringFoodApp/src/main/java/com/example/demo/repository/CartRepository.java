package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.demo.model.Cart;

public interface CartRepository extends MongoRepository<Cart, String> {
	@Query(value="{'id' : $0}", delete = true)
	public void deleteById (String id);
}
