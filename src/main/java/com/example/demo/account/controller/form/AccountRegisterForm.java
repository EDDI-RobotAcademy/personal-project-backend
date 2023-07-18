package com.example.demo.account.controller.form;

import com.example.demo.account.entity.RoleType;
import com.example.demo.account.service.request.AccountRegisterRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class AccountRegisterForm {
    private String email;
    private String password;
//    private RoleType roleType;

//    public AccountRegisterForm(String email, String password) {
//        this.email = email;
//        this.password = password;
//    }

    public AccountRegisterRequest toAccountRegisterRequest() {
        return new AccountRegisterRequest(email, password);
    }
}