package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.account.entity.Account;

public interface AccountService {

    Account register(AccountRegisterRequestForm requestForm);
}
