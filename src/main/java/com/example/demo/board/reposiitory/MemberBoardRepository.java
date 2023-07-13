package com.example.demo.board.reposiitory;


import com.example.demo.board.entity.MemberBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberBoardRepository extends JpaRepository<MemberBoard, Long> {
    List<MemberBoard> findAll();
}
