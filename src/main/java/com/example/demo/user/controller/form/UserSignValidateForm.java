package com.example.demo.user.controller.form;

import com.example.demo.user.service.request.UserSignInRequest;
import com.example.demo.user.service.request.UserSignUpRequest;
import com.example.demo.user.service.request.UserSignValidateRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor

public class UserSignValidateForm {
    final private String email;
    final private String nickName;
    public UserSignValidateRequest toUserSignValidateRequest () {
        return new UserSignValidateRequest(email,nickName);
    }
}
