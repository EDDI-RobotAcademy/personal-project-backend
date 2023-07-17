package com.jinho.project.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @Getter
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String userName;
    private String nickname;
    private String password;

    public Account(String email, String userName, String nickname, String password) {
        this.email = email;
        this.userName = userName;
        this.nickname = nickname;
        this.password = password;
    }
}
