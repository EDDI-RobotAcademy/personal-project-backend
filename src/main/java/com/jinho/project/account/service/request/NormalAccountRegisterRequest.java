package com.jinho.project.account.service.request;

import com.jinho.project.account.entity.Account;
import com.jinho.project.account.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class NormalAccountRegisterRequest {

    private String email;
    private String userName;
    private String nickname;
    private String password;
    private RoleType roleType;

    public Account toAccount () {
        return new Account(email, userName, nickname, password);
    }

}
