package com.example.demo.board.community.repository;

import com.example.demo.board.community.entity.CommunityBoard;
import com.example.demo.board.notice.entity.NoticeBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<CommunityBoard, Long> {
    Optional<CommunityBoard> findByCommunityTitle(String communityTitle);
    Optional<CommunityBoard> findByCommunityNumber(Long communityNumber);

}
