package com.example.demo.board.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.util.Locale;

@Entity
//Table(name = 'boardCategoryType') <- ""아니면 먹히지 않음
@Table(name = "boardCategoryType")
@NoArgsConstructor
public class BoardCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "board_id")
    private Board board;
    @Enumerated(EnumType.STRING)
    private BoardCategoryType boardCategoryType;

    public BoardCategory(BoardCategoryType boardCategoryType) {
        this.boardCategoryType = boardCategoryType;
    }

    public BoardCategoryType getBoardCategoryType() {
        return boardCategoryType;
    }
}
