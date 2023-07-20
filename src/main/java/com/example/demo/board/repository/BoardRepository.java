package com.example.demo.board.repository;

import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<Board> findById(Long boardId);
    List<Board> findByTitleContaining(String keyword);
    @Query("SELECT b FROM Board b WHERE b.boardCategory = :category ORDER BY b.createDate DESC")
    List<Board> findAllCategory(@Param("category")BoardCategory category);
    @Query("SELECT COUNT(b) FROM Board b WHERE b.boardCategory = :category")
    long findPostNumberByCategory(@Param("category")BoardCategory boardCategory);
}
