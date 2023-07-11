package com.example.demo.board.shop.repository;

import com.example.demo.board.shop.entity.ShopBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopRepository extends JpaRepository<ShopBoard, Long> {
    Optional<ShopBoard> findByShopId(Long shopId);

}
