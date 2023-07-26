package com.example.demo.review.repository;

import com.example.demo.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByRestaurantId(Long restaurantId);
    List<Review> findByRestaurantId(Long restaurantId);
}
