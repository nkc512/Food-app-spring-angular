package com.example.demo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Dish;

@Repository
public interface DishRepository extends MongoRepository<Dish, Long> {
List<Dish> findByCafeteriaId(Long cafeterianId);
Optional<Dish> findByIdAndCafeteriaId(Long id,Long cafeteriaid);
List<Dish> findByDishnameContainingAndCafeteriaId(String searchval,Long id);
List<Dish> findByDishnameContaining(String searchval);
}
