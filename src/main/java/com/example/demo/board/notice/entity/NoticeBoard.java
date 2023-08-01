package com.example.demo.board.notice.entity;

import com.example.demo.account.entity.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class NoticeBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;
    @Setter
    private String title;
    @Setter
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    @CreationTimestamp
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public NoticeBoard(String title, String content, Long accountId) {
        this.title = title;
        this.content = content;
        this.account = new Account(accountId);
    }

    public NoticeBoard(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public NoticeBoard(Long noticeId, String title, String content) {
        this.noticeId = noticeId;
        this.title = title;
        this.content = content;
    }
}
