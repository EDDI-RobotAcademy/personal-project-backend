package com.example.demo.restaurant.repository;

import com.example.demo.restaurant.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM Menu m WHERE m.restaurant.id = :restaurantId")
    void deleteByRestaurantId(@Param("restaurantId") Long restaurantId);

    @Query("SELECT m FROM Menu m JOIN FETCH m.restaurant r WHERE r.id = :restaurantId")
    Optional<Menu> findByRestaurantId(@Param("restaurantId") Long restaurantId);
}
