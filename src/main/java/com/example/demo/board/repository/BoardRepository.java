package com.example.demo.board.repository;

import com.example.demo.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    @Query("SELECT b FROM Board b WHERE b.title LIKE %:title%")
    List<Board> findByKeyword(String title);
}
