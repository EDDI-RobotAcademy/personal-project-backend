package com.example.demo.account.controller.form.business;

import com.example.demo.account.entity.RoleType;
import com.example.demo.account.service.request.BusinessAccountRegisterRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BusinessAccountRegisterForm {

    final private String email;
    final private String password;
    final private RoleType roleType;
    final private Long businessNumber; //사업자 등록번호

    public BusinessAccountRegisterRequest toAccountRegisterRequest () {

        return new BusinessAccountRegisterRequest(
                email, password, roleType, businessNumber);
    }
}
