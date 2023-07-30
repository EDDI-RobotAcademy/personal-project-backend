package com.example.demo.account.controller.form.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountLoginRequestForm {
    private String email;
    private String password;
}
