package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountDeleteRequestForm;
import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountModifyRequestForm;
import com.example.demo.account.controller.form.AccountRegistRequestForm;
import com.example.demo.account.entity.Account;

import java.util.List;

public interface AccountService {
    Account regist(AccountRegistRequestForm requestForm);

    Account login(AccountLoginRequestForm requestForm);

    Boolean checkEmail(String email);

    List<Account> list();

    Boolean delete(AccountDeleteRequestForm accountDeleteRequestForm);

    Account modify(String email, AccountModifyRequestForm accountModifyRequestForm);
}
