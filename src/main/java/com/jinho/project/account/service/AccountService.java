package com.jinho.project.account.service;

import com.jinho.project.account.controller.form.AccountLoginRequestForm;
import com.jinho.project.account.service.request.NormalAccountRegisterRequest;

public interface AccountService {
    Boolean normalAccountRegister(NormalAccountRegisterRequest request);

    String login(AccountLoginRequestForm requestForm);

    Long findAccountIdByEmail(String email);


}
