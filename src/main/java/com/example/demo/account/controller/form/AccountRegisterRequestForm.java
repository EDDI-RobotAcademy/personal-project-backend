package com.example.demo.account.controller.form;

import com.example.demo.account.controller.request.AccountRegisterRequest;
import com.example.demo.account.entity.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class AccountRegisterRequestForm {

    final private String email;
    final private String password;
    final private String name;
    final private String phoneNumber;
    final private RoleType roleType;

    public AccountRegisterRequest toAccountRegisterRequest() {
        return new AccountRegisterRequest(email, password, name, phoneNumber, roleType);
    }
}
