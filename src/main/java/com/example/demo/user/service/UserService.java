package com.example.demo.user.service;

public interface UserService {
    Boolean checkEmailDuplication(String email);

    Boolean signUp(UserRegisterRequest request);
}
