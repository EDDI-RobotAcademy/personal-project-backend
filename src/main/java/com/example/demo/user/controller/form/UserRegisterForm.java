package com.example.demo.user.controller.form;

import com.example.demo.user.entity.RoleType;
import com.example.demo.user.service.request.UserSignUpRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
public class UserRegisterForm {
    final private String uid;
    final private String email;
    final private String name;
    final private String nickName;


    public UserSignUpRequest toUserSignUpRequest () {
        return new UserSignUpRequest(uid,email,name,nickName);
    }

}
