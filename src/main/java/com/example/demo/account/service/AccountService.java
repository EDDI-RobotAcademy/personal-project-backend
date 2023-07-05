package com.example.demo.account.service;

import com.example.demo.account.service.request.BusinessAccountRegisterRequest;
import com.example.demo.account.service.request.NormalAccountRegisterRequest;

public interface AccountService {
    Boolean normalAccountRegister(NormalAccountRegisterRequest request);
    Boolean businessAccountRegister(BusinessAccountRegisterRequest request);
    Boolean checkEmailDuplication(String email);
}
