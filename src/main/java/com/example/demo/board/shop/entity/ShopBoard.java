package com.example.demo.board.shop.entity;

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
public class ShopBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;
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

    public ShopBoard(String title, String content, Long accountId, String image) {
        this.title = title;
        this.content = content;
        this.account = new Account(accountId);
        this.image = image;
    }

    public ShopBoard(Long shopId, String title, String content, String image) {
        this.shopId = shopId;
        this.title = title;
        this.content = content;
        this.image = image;
    }
}
