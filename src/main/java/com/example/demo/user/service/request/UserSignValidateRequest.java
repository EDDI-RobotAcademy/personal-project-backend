package com.example.demo.user.service.request;

import com.example.demo.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignValidateRequest {
    final private String email;
    final private String nickName;

    public User toUserCheck () {
        return new User(email,nickName);
    }
}
