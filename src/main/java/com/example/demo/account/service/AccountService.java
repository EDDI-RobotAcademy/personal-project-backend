package com.example.demo.account.service;

import com.example.demo.account.controller.request.AccountSignUpRequest;

public interface AccountService {
    Boolean signUp(AccountSignUpRequest signUpRequest);
}
