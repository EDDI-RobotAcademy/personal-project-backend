package com.example.demo.restaurant.repository;

import com.example.demo.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findAllByAccountId(Long accountId);
    Optional<Restaurant> findByRestaurantName(String restaurantName);
}
