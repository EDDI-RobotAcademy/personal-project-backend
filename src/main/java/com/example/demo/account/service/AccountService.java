package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountLoginResponseForm;
import com.example.demo.account.controller.form.AccountModifyRequestForm;
import com.example.demo.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.account.entity.Account;

public interface AccountService {

    Account register(AccountRegisterRequestForm requestForm);
    Boolean duplicateCheckEmail(String email);
    Boolean duplicateCheckNickname(String nickname);
    AccountLoginResponseForm login(AccountLoginRequestForm requestForm);
    Account modify(AccountModifyRequestForm requestForm);
    Boolean logout(String userToken);
}
