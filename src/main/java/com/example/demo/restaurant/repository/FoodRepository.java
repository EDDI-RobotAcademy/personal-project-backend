package com.example.demo.restaurant.repository;

import com.example.demo.restaurant.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
