package com.example.demo.user.service;

import com.example.demo.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserRegisterRequest {
    final private String email;

    public User toUser(){
        return new User(email);
    }
}
