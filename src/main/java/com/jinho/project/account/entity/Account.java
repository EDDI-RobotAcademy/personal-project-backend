package com.jinho.project.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @Getter
    @Column(name = "account_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Setter
    private String userName;

    @Setter
    private String nickname;

    private String password;

    public Account(String email, String userName, String nickname, String password) {
        this.email = email;
        this.userName = userName;
        this.nickname = nickname;
        this.password = password;
    }


}
