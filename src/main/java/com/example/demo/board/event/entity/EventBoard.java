package com.example.demo.board.event.entity;

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
public class EventBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
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
    @Setter
    private String image;

    public EventBoard(String title, String content, Long accountId, String image) {
        this.title = title;
        this.content = content;
        this.account = new Account(accountId);
        this.image = image;
    }

    public EventBoard(Long eventId, String title, String content, String image) {
        this.eventId = eventId;
        this.title = title;
        this.content = content;
        this.image = image;
    }
}
