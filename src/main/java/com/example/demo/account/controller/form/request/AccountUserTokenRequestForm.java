package com.example.demo.account.controller.form.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;


@Getter
@NoArgsConstructor
public class AccountUserTokenRequestForm {
    private String userToken;

    public AccountUserTokenRequestForm(String userToken) {
        this.userToken = userToken;
    }
}


