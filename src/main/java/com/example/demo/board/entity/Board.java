package com.example.demo.board.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
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
    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer likeCount = 0;
    @Setter
    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer readCount;
    @Setter
    @Generated(GenerationTime.ALWAYS)
    @Column(name = "reply_count", nullable = false, columnDefinition = "int default 0")
    private Integer replyCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private LocalDateTime createDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private LocalDateTime modifyDate;
    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;

    public Board(String writer, String title, String content, Integer likeCount, Integer readCount, Integer replyCount, BoardCategory boardCategory) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
        this.readCount = readCount;
        this.replyCount = replyCount;
        this.boardCategory = boardCategory;
    }

    public Board(String writer, String title, String content, BoardCategory boardCategory) {
        this.writer = writer;
        this.title = title;
        this.content = content;
        this.boardCategory = boardCategory;
    }

    public void updateReadCount(Integer readCount) {
        this.readCount = readCount;
    }
}