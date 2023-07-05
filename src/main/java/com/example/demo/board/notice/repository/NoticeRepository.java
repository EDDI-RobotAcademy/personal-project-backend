package com.example.demo.board.notice.repository;

import com.example.demo.board.notice.entity.NoticeBoard;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoticeRepository extends JpaRepository<NoticeBoard, Long> {
    Optional<NoticeBoard> findByNoticeTitle(String noticeTitle);
    Optional<NoticeBoard> findByNoticeNumber(Long noticeNumber);
}
