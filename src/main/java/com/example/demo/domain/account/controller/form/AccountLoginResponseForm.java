package com.example.demo.domain.account.controller.form;

import com.example.demo.authentication.jwt.TokenInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class AccountLoginResponseForm {
    private TokenInfo tokenInfo;
    private String nickname;

    public AccountLoginResponseForm(TokenInfo tokenInfo, String nickname) {
        this.tokenInfo = tokenInfo;
        this.nickname = nickname;
    }
}
