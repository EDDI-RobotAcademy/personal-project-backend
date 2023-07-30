package com.example.demo.board.repository;

import com.example.demo.board.entity.Board;
import com.example.demo.board.entity.BoardLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    List<BoardLike> findByBoard_BoardIdAndUserId(Long boardId,Long userId);
}
