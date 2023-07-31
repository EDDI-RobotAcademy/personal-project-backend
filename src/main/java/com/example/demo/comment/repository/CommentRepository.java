package com.example.demo.comment.repository;

import com.example.demo.board.entity.Board;
import com.example.demo.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findById(Long commentId);
    List<Comment> findByBoard_BoardId(Long boardId);
}
