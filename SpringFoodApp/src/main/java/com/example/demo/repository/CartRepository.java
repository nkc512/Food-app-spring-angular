package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Cart;
@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
}
