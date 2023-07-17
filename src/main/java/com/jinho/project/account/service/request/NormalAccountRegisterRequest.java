package com.jinho.project.account.service.request;

import com.jinho.project.account.entity.Account;
import com.jinho.project.account.entity.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class NormalAccountRegisterRequest {

    final private String email;
    final private String userName;
    final private String nickname;
    final private String password;
    final private RoleType roleType;

    public Account toAccount () {
        return new Account(email, userName, nickname, password);
    }

}
