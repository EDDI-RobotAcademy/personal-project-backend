package com.example.demo.account.controller.form;

import com.example.demo.account.entity.RoleType;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AccountLoginRequestForm {

    final private String email;

    final private String password;

    public AccountLoginRequestForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
