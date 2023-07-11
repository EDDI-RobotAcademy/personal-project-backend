package com.example.demo.domain.account.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountModifyRequestForm {
    final private String nickname;
    final private String password;
}
