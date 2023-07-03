package com.example.demo.user.controller.form;

import com.example.demo.user.service.UserRegisterRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class UserRegisterForm {
    private String email;

    public UserRegisterForm(String email) {
        this.email = email;
    }
    public UserRegisterRequest toUserRegisterRequest(){
        return new UserRegisterRequest(email);
    }
}
