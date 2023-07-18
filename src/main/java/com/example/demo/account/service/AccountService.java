package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountLoginResponseForm;
import com.example.demo.account.service.request.AccountRegisterRequest;

public interface AccountService {
    Boolean checkEmailDuplication(String email);

    Boolean signUp(AccountRegisterRequest request);

    AccountLoginResponseForm login(AccountLoginRequestForm requestForm);
}