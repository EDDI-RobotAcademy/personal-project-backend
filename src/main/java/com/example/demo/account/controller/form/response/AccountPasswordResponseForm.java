package com.example.demo.account.controller.form.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountPasswordResponseForm {
    final private String password;
    final private String passwordFindStatus;

}
