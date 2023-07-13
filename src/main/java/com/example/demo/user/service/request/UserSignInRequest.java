package com.example.demo.user.service.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserSignInRequest {
    final private String email;
    final private String password;
}
