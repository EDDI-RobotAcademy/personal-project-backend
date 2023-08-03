package com.jinho.project.account.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AccountMyPageResponseForm {
    final private String email;
    final private String userName;
    final private String nickname;

}
