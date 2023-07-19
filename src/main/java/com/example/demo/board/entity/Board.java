package com.example.demo.board.entity;

import com.example.demo.account.entity.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    private String title;

    private String writer;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public Board(String title, String writer, String content, Account account) {
        this.title = title;
        this.writer = writer;
        this.content = content;
        this.account = account;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private LocalDateTime createdData;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @UpdateTimestamp
    private LocalDateTime updateData;
}
