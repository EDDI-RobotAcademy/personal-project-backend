package com.example.demo.restaurant.repository;

import com.example.demo.restaurant.entity.RestaurantImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RestaurantImagesRepository extends JpaRepository<RestaurantImages, Long> {
    @Query("SELECT ri FROM RestaurantImages ri JOIN FETCH ri.restaurant r WHERE r.id = :restaurantId")
    List<RestaurantImages> findByRestaurantId(@Param("restaurantId") Long restaurantId);
    @Transactional
    void deleteAllByRestaurantId(Long id);
}
