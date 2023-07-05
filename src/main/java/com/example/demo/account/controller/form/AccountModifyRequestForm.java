package com.example.demo.account.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountModifyRequestForm {
    final private String userToken;
    final private String nickname;
    final private String password;
}
