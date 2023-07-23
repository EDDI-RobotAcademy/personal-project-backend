package com.example.demo.map.repository;

import com.example.demo.map.entity.BoardMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardMapRepository extends JpaRepository<BoardMap, Long> {
}
