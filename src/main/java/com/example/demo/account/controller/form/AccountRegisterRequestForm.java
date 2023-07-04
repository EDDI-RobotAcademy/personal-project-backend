package com.example.demo.account.controller.form;

import com.example.demo.account.entity.Account;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountRegisterRequestForm {
    final private String email;
    final private String password;
    final private String nickname;

    public Account toAccount(){
        return new Account(email, password, nickname);
    }
}
