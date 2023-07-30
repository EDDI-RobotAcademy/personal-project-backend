package com.example.demo.user.entity;

import com.example.demo.board.entity.Board;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "Bookmark")
@Entity(name = "Bookmark")
@Getter
@Setter
@NoArgsConstructor
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookMarkId;

    @Column(name = "user_id")
    private Long userId;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    public Bookmark(Long userId, Board board) {
        this.userId = userId;
        this.board = board;
    }
}
