package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Cafeteria;

@Repository
public interface CafeteriaRepository extends MongoRepository<Cafeteria,Long> {
	
	public Optional<Cafeteria> findById(Long id);
}
