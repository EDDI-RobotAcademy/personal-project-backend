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
    private Long boardId;
    private String writer;
    @Setter
    private String title;
    @Setter
    private String content;
    @Setter
    private Integer likeCount;
    @Setter
    private Integer readCount;
    @Setter
    private Integer replyCount;
//    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
//    private Set<BoardCategory> boardCategories = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime modifyDate;

    public Board(String writer, String title, String content, Integer likeCount, Integer readCount, Integer replyCount) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.readCount = readCount;
        this.replyCount = replyCount;
    }
    public Board(String writer, String title, String content) {
        this.writer = writer;
        this.title = title;
        this.content = content;
    }
}
