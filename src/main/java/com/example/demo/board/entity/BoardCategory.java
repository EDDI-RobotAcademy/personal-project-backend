package com.example.demo.board.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "boardCategoryType")
@NoArgsConstructor
public class BoardCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id")
    private Board board;

    @Enumerated(EnumType.STRING)
    private BoardCategoryType boardCategoryType;

    public BoardCategory(BoardCategoryType boardCategoryType) {
        this.boardCategoryType = boardCategoryType;
    }

    public static BoardCategory fromValue(int value) {
        switch (value) {
            case 10:
                return new BoardCategory(BoardCategoryType.MAIN);
            case 20:
                return new BoardCategory(BoardCategoryType.SPRING);
            case 30:
                return new BoardCategory(BoardCategoryType.PYTHON);
            case 40:
                return new BoardCategory(BoardCategoryType.VUE);
            case 50:
                return new BoardCategory(BoardCategoryType.REACT);
            case 60:
                return new BoardCategory(BoardCategoryType.QUESTION);
            default:
                throw new IllegalArgumentException("Invalid value for BoardCategory: " + value);
        }
    }

    public BoardCategoryType getBoardCategoryType() {
        return boardCategoryType;
    }
}
