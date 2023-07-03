package com.example.demo.account.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Setter
    private String email;
    @Setter
    private String password;
    @Setter
    private String accountName;
    @Setter
    private String accountBirth;
    @Setter
    private String accountPhone;
    @Setter
    private String accountAddress;
    @Setter
    private String userToken;

    public Account(String email, String password, String accountName, String accountBirth, String accountPhone, String accountAddress) {
        this.email = email;
        this.password = password;
        this.accountName = accountName;
        this.accountBirth = accountBirth;
        this.accountPhone = accountPhone;
        this.accountAddress = accountAddress;
    }

    public Account(String userToken) {
        this.id = id;
        this.email = email;
        this.password = password;

    }

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }
}