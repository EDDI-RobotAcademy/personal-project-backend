package com.example.demo.like.repository;

import com.example.demo.like.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {
    Optional<LikeEntity> findByAccountIdAndRestaurantId(Long accountId, Long restaurantId);
    @Query("SELECT COUNT(DISTINCT l.account.id) FROM LikeEntity l WHERE l.restaurant.id = :restaurantId")
    Integer countLikesByRestaurantId(@Param("restaurantId") Long restaurantId);
}
