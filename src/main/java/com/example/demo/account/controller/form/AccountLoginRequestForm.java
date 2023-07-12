package com.example.demo.account.controller.form;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AccountLoginRequestForm {

    private String email;

    private String password;

    public AccountLoginRequestForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public void setEmail(String accountToken, String refreshToken) {
        this.email = accountToken;
        this.email = refreshToken;
    }
}
