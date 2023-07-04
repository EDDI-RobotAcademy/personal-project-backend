package com.example.demo.account.controller.form;

import com.example.demo.account.controller.request.AccountSignUpRequest;
import com.example.demo.account.entity.Account;
import com.example.demo.account.entity.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class AccountSignUpRequestForm {

    final private String email;

    final private String password;

    final private String name;

    final private String phoneNumber;

    final private RoleType roleType;

    public AccountSignUpRequest toAccountSignUpRequest() {
        return new AccountSignUpRequest(email, password, name, phoneNumber, roleType);
    }
}
