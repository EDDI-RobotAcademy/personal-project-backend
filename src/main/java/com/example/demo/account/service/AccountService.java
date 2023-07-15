package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.request.AccessRegisterRequest;
import com.example.demo.account.controller.request.AccountRegisterRequest;
import com.example.demo.account.controller.request.MyPageRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.entity.RoleType;
import com.example.demo.security.jwt.subject.TokenResponse;

public interface AccountService {
    Boolean signUp(AccountRegisterRequest request);

    Boolean accessSignUp(AccessRegisterRequest accessRegisterRequest);

    Boolean checkEmail(String email);

    TokenResponse login(AccountLoginRequestForm form);

    Boolean findAccountInfo(MyPageRequestForm form, String accessToken);
}
