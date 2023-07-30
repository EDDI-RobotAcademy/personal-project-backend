package com.example.demo.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Getter
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Setter
    private String password;
    private String userName;
    private String gender;
    private String birth;
    @Setter
    private String nickName;
    @Setter
    private String address;

    public Account(String email, String password, String userName, String gender, String birth, String nickName, String address) {
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.gender = gender;
        this.birth = birth;
        this.nickName = nickName;
        this.address = address;
    }
}
