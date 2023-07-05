package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountResponseForm;
import com.example.demo.account.service.request.AccountRegisterRequest;

public interface AccountService {
    Boolean checkEmailDuplication(String email);

    Boolean signUp(AccountRegisterRequest request);
}