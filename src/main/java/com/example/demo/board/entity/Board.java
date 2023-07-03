package com.example.demo.board.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String user;
    @Setter
    private String title;
    @Setter
    private String content;
    private Integer boardLike;
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    private Set<BoardCategory> boardCategories = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime modifyDate;

    public Board(Long id, String user, String title, String content, Integer boardLike) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.boardLike = boardLike;
    }
}
