package com.example.demo.user.service;

import com.example.demo.user.controller.form.UserSignInResponseForm;
import com.example.demo.user.entity.RoleType;
import com.example.demo.user.service.request.UserSignInRequest;
import com.example.demo.user.service.request.UserSignUpRequest;

public interface UserService {
    Boolean signUp(UserSignUpRequest userSignUpRequest);
    UserSignInResponseForm signIn(UserSignInRequest request);
    RoleType lookup(String userToken);
    Long findUserId(String userToken);
}
