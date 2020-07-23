package com.example.demo.repository;

import java.util.ArrayList;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;
import com.example.demo.model.Payment;
@Repository
public interface PaymentRepository extends CrudRepository<Payment,String>{

	Payment findTopByUsername(String username,Sort sort);
}
