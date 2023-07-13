package com.example.demo.account.controller.form.business;

import com.example.demo.account.entity.RoleType;
import com.example.demo.account.service.request.BusinessAccountRegisterRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessAccountRegisterForm {

    private String email;
    private String password;
    private RoleType roleType;
    private Long businessNumber; //사업자 등록번호

    public BusinessAccountRegisterRequest toAccountRegisterRequest () {

        return new BusinessAccountRegisterRequest(
                email, password, roleType, businessNumber);
    }
}
