package com.example.demo.user.repository;

import com.example.demo.board.entity.BoardLike;
import com.example.demo.user.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findByBoard_BoardIdAndUserId(Long boardId, Long userId);
    void deleteByBoard_BoardIdAndUserId(Long boardId, Long userId);
    List<Bookmark> findByUserId(Long userId);
}
