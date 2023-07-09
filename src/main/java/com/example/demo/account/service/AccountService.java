package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.request.AccessRegisterRequest;
import com.example.demo.account.controller.request.AccountRegisterRequest;
import com.example.demo.account.entity.RoleType;

public interface AccountService {
    Boolean signUp(AccountRegisterRequest request);

    Boolean accessSignUp(AccessRegisterRequest accessRegisterRequest);

    Boolean checkEmail(String email);

    String login(AccountLoginRequestForm form);

    Long findAccountInfoById(String email);

}
