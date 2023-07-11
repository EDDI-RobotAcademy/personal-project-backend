package com.example.demo.account.controller.request;

import com.example.demo.account.entity.Account;
import com.example.demo.account.entity.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountRegisterRequest {

    final private String email;

    final private String password;

    final private String name;

    final private String phoneNumber;

    final private RoleType roleType;

    public Account toAccount() {
        return new Account(email, password, name, phoneNumber, roleType);
    }
}
