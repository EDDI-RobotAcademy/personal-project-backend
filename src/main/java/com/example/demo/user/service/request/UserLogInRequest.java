package com.example.demo.user.service.request;
import com.example.demo.user.entity.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserLogInRequest {
    final private String name;
    final private String nickName;

    public User toUser () {
        return new User(name,nickName);
    }
}