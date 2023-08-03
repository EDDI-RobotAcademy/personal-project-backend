package com.jinho.project.account.service;

import com.jinho.project.account.controller.form.*;
import com.jinho.project.account.entity.Account;
import com.jinho.project.account.entity.RoleType;
import com.jinho.project.account.service.request.NormalAccountRegisterRequest;

public interface AccountService {
    Boolean normalAccountRegister(NormalAccountRegisterRequest request);

    AccountLoginResponseForm login(AccountLoginRequestForm requestForm);

    Long findAccountIdByEmail(String email);


    Boolean checkEmailDuplication(String email);

    Long findAccountId(String userToken);

    String findAccountNickname(Long accountId);

    String findAccountEmail(Long accountId);

    String lookupUserName(Long accountId);

    String lookupNickName(Long accountId);

    Account read(AccountReadRequestForm requestForm);

    void delete(Long id);


    Account modifyUsername(Long id, NormalAccountRegisterRequest request);

    Account modifyNickname(Long id, NormalAccountRegisterRequest request);

    RoleType lookup(String userToken);
}
