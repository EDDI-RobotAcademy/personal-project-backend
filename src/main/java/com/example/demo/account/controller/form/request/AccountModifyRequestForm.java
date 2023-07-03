package com.example.demo.account.controller.form.request;


import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public class AccountModifyRequestForm {

    final private String email;
    final private String password;



}
