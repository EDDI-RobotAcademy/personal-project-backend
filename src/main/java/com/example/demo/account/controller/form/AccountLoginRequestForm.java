package com.example.demo.account.controller.form;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AccountLoginRequestForm {

    final private String email;

    final private String password;

    final private String accessToken;

    public AccountLoginRequestForm(String email, String password, String accessToken) {
        this.email = email;
        this.password = password;
        this.accessToken = accessToken;
    }
}
