package com.example.demo.user.controller.form;

import com.example.demo.user.service.request.UserSignInRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignInRequestForm {
    final private String email;
    final private String password;

    public UserSignInRequest toUserSignInRequest () {
        return new UserSignInRequest(email,password);
    }
}
