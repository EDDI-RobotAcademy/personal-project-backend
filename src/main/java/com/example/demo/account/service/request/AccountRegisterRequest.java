package com.example.demo.account.service.request;

import com.example.demo.account.entity.MemberAccount;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountRegisterRequest {
    final private String email;
    final private String password;

    public MemberAccount toAccount() {
        return new MemberAccount(email, password);
    }
}