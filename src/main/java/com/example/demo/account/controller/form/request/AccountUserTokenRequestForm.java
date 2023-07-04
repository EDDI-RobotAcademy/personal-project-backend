package com.example.demo.account.controller.form.request;


import com.example.demo.account.entity.Account;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class AccountUserTokenRequestForm {
    final private String userToken;

}


