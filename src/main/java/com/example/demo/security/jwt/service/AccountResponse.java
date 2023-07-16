package com.example.demo.security.jwt.service;

import com.example.demo.account.entity.Account;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AccountResponse {

    private Long accountId;

    private String email;

    public AccountResponse(Long accountId, String email) {
        this.accountId = accountId;
        this.email = email;
    }

    public static AccountResponse of(Account account) {
        return new AccountResponse(account.getId(), account.getEmail());
    }
}
