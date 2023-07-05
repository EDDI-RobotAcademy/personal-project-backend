package com.example.demo.account.controller.form;

import com.example.demo.account.entity.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AccountRegisterRequestForm {
    final private String email;
    @Getter
    final private String password;
    final private String nickname;


    public Account toAccount(String password){
        return new Account(email, password, nickname);
    }

}
