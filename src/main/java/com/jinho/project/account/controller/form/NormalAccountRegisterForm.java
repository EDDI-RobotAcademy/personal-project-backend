package com.jinho.project.account.controller.form;

import com.jinho.project.account.entity.RoleType;
import com.jinho.project.account.service.request.NormalAccountRegisterRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NormalAccountRegisterForm {


    final private String email;
    final private String userName;
    final private String nickname;
    final private String password;
    final private RoleType roleType;

    public NormalAccountRegisterRequest toAccountRegisterRequest () {

        return new NormalAccountRegisterRequest(
                email, userName, nickname, password, roleType);
    }
}
