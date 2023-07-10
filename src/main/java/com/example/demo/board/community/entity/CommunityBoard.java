package com.example.demo.board.community.entity;

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
public class CommunityBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long communityId;
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

    public CommunityBoard(String title, String content,Long accountId) {
        this.title = title;
        this.content = content;
        this.account = new Account(accountId);
    }

    public CommunityBoard(Long communityId, String title, String content) {
        this.communityId = communityId;
        this.title = title;
        this.content = content;
    }
}
