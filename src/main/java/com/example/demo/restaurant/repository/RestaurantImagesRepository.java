package com.example.demo.restaurant.repository;

import com.example.demo.restaurant.entity.RestaurantImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RestaurantImagesRepository extends JpaRepository<RestaurantImages, Long> {
    List<RestaurantImages> findByRestaurantId(Long id);
    @Transactional
    void deleteAllByRestaurantId(Long id);
}
