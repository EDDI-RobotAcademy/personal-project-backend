package com.example.demo.user.service.request;

import com.example.demo.user.entity.RoleType;
import com.example.demo.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignUpRequest {
    final private String uid;
    final private String email;
    final private String name;
    final private String nickName;

    public User toUser () {
        return new User(uid,email,name,nickName);
    }
}
