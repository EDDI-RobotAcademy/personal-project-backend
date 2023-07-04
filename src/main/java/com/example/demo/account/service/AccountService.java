package com.example.demo.account.service;

import com.example.demo.account.controller.request.AccountRegisterRequest;

public interface AccountService {
    Boolean signUp(AccountRegisterRequest request);


}
