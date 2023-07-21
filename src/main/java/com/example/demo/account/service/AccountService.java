package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.entity.RoleType;
import com.example.demo.account.service.request.BusinessAccountRegisterRequest;
import com.example.demo.account.service.request.NormalAccountRegisterRequest;

public interface AccountService {
    Boolean normalAccountRegister(NormalAccountRegisterRequest request);
    Boolean businessAccountRegister(BusinessAccountRegisterRequest request);
    Boolean checkEmailDuplication(String email);
    String login(AccountLoginRequestForm accountLoginRequestForm);
    RoleType lookup(String userToken);
    Long findAccountId(String userToken);
    Boolean businessCheck(Long businessId);
    String returnEmail(String userToken);
}
