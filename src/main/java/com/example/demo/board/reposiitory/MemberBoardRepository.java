package com.example.demo.board.reposiitory;


import com.example.demo.board.entity.MemberBoard;
import io.lettuce.core.GeoArgs;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberBoardRepository extends JpaRepository<MemberBoard, Long> {
    @Query("SELECT b FROM MemberBoard b JOIN FETCH b.member left join fetch filePathList")
    List<MemberBoard> findAll(Sort sort);
    @Query("SELECT b FROM MemberBoard b JOIN FETCH b.member")
    List<MemberBoard> findAllwithPage(Pageable pageable);

    @Query("SELECT b FROM MemberBoard b JOIN FETCH b.member left join fetch filePathList WHERE b.boardId = :boardId")
    Optional<MemberBoard> findByIdWithMember(Long boardId);
    @Query("SELECT b FROM MemberBoard b JOIN FETCH b.member WHERE LOWER(b.content) LIKE %:keyword%")
    List<MemberBoard> findByContentContaining(String keyword);
}
