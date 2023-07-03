package com.example.demo.account.service;

import com.example.demo.account.controller.form.request.*;
import com.example.demo.account.controller.form.response.AccountLoginResponseForm;
import com.example.demo.account.controller.form.response.AccountPasswordResponseForm;
import com.example.demo.account.entity.Account;

import java.util.List;

public interface AccountService {
    Account regist(AccountRegistRequestForm requestForm);

    AccountLoginResponseForm login(AccountLoginRequestForm requestForm);

    Boolean checkEmail(String email);

    List<Account> list();

    Boolean delete(AccountDeleteRequestForm accountDeleteRequestForm);

    Account modify(String email, AccountModifyRequestForm accountModifyRequestForm);

    AccountPasswordResponseForm passwordFind(AccountPasswordFindRequestForm requestForm);
}
