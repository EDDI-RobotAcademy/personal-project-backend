package com.example.demo.account.controller.form.request;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class AccountModifyRequestForm {

    final private String userToken;

    final private String password;
    final private String accountName;
    final private String accountBirth;
    final private String accountPhone;
    final private String accountAddress;

}
