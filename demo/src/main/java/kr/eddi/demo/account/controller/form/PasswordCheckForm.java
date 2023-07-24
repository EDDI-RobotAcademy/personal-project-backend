package kr.eddi.demo.account.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter

@RequiredArgsConstructor
public class PasswordCheckForm {

    String nickName;
    String password;
}
