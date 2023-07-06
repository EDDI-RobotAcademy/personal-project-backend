package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountModifyRequestForm;
import com.example.demo.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.authentication.jwt.TokenInfo;

public interface AccountService {

    Account register(AccountRegisterRequestForm requestForm);

    Boolean duplicateCheckEmail(String email);

    Boolean duplicateCheckNickname(String nickname);

    TokenInfo login(AccountLoginRequestForm requestForm);

    Account modify(AccountModifyRequestForm requestForm);

    Boolean logout(String userToken);

    Boolean withdrawal(String userToken);

    Account getLoginAccountByEmail(String email);

    Account getLoginAccountById(Long userId);
}
