package com.example.demo.account.controller.form.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountPasswordFindRequestForm {
    final private String email;
    final private String accountName;
    final private String accountBirth;
}
