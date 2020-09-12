package com.example.demo.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Payment;
@Repository
public interface PaymentRepository extends CrudRepository<Payment,String>{

	Payment findTopByUsername(String username,Sort sort);
}
