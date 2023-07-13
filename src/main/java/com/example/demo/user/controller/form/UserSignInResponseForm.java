package com.example.demo.user.controller.form;

import com.example.demo.user.service.request.UserSignInRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignInResponseForm {
    final private String userToken;
    final private String roleType;


}
