package com.example.demo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Dish;

@Repository
public interface DishRepository extends MongoRepository<Dish, Long> {
List<Dish> findByCafeterianame(String cafeteriaId);
Optional<Dish> findByIdAndCafeterianame(Long id,String cafeterianame);
List<Dish> findByDishnameContainingAndCafeterianame(String searchval,String cafeterianame);
List<Dish> findByDishnameContaining(String searchval);
}
