package com.example.demo.account.controller.form.request;


import com.example.demo.account.entity.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class AccountDeleteRequestForm {
    final private String userToken;

    public Account toAccount(){
        return new Account(userToken);
    }
}


