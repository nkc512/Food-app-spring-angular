package com.example.demo.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Feedback;

public interface FeedbackRepository extends MongoRepository<Feedback, String>{
	ArrayList<Feedback> findByUsername(String username);
	@Override
	Optional<Feedback> findById(String id);
	Optional<Feedback> findByIdAndUsername(String id,String username);
}
