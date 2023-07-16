package com.example.demo.user.service;

import com.example.demo.user.controller.form.UserSignInResponseForm;
import com.example.demo.user.entity.RoleType;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.request.UserLogInRequest;
import com.example.demo.user.service.request.UserSignInRequest;
import com.example.demo.user.service.request.UserSignUpRequest;
import com.example.demo.user.service.request.UserSignValidateRequest;

import java.util.List;

public interface UserService {
    User logIn(String uid);
    Boolean signUp(UserSignUpRequest userSignUpRequest);
    List<String> SignValidate(UserSignValidateRequest userSignValidateRequest);
    UserSignInResponseForm signIn(UserSignInRequest request);
    RoleType lookup(String userToken);
    Long findUserId(String userToken);
}
