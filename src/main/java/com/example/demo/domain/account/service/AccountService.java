package com.example.demo.domain.account.service;

import com.example.demo.domain.account.controller.form.*;
import com.example.demo.domain.account.entity.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AccountService {

    Account register(AccountRegisterRequestForm requestForm);

    Boolean duplicateCheckEmail(String email);

    Boolean duplicateCheckNickname(String nickname);

    AccountLoginResponseForm login(AccountLoginRequestForm requestForm);

    Account modify(AccountModifyRequestForm requestForm, HttpServletRequest request);

    Boolean logout(HttpServletResponse response);

    Boolean withdrawal(String userToken);

    Account getLoginAccountByEmail(String email);

    boolean duplicateCheckPassword(AccountPasswordCheckRequestForm requestForm, HttpServletRequest request);
}
