package com.example.demo.account.controller.form;

import com.example.demo.account.controller.request.AccessRegisterRequest;
import com.example.demo.account.entity.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class AccessRegisterRequestForm {

    final private String email;

    final private String password;

    final private String accessNumber;

    final private RoleType roleType;


    public AccessRegisterRequest toAccessRegisterRequest() {
        return new AccessRegisterRequest(email, password, accessNumber, roleType);
    }
}
