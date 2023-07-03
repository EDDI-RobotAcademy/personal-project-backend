package com.example.demo.account.service.request;

import com.example.demo.account.entity.Account;
import com.example.demo.account.entity.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessAccountRegisterRequest {
    final private String email;
    final private String password;
    final private RoleType roleType;
    final private Long businessNumber;

    public Account toAccount () {
        return new Account(email, password);
    }
}
