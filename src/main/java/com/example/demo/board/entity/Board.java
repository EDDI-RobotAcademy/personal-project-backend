package com.example.demo.board.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private Integer likeCount;
    @Setter
    private Integer readCount;

    public Board addReadCount(Integer readCount) {
        this.readCount = readCount+1;
        return this;
    }

    @Setter
    private Integer replyCount;
//    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
//    private Set<BoardCategory> boardCategories = new HashSet<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
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
