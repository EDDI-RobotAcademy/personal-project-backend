package com.example.demo.domain.account.service;

import com.example.demo.domain.account.controller.form.AccountLoginRequestForm;
import com.example.demo.domain.account.controller.form.AccountModifyRequestForm;
import com.example.demo.domain.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.domain.account.entity.Account;
import com.example.demo.authentication.jwt.TokenInfo;
import jakarta.servlet.http.HttpServletResponse;

public interface AccountService {

    Account register(AccountRegisterRequestForm requestForm);

    Boolean duplicateCheckEmail(String email);

    Boolean duplicateCheckNickname(String nickname);

    TokenInfo login(AccountLoginRequestForm requestForm);

    Account modify(String email, AccountModifyRequestForm requestForm);

    Boolean logout(HttpServletResponse response);

    Boolean withdrawal(String userToken);

    Account getLoginAccountByEmail(String email);

    Account getLoginAccountById(Long userId);
}
