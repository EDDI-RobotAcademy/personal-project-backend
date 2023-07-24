package com.example.demo.account.controller.form.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountGoMypageForm {
    final private String userToken;
    final private String password;
}
