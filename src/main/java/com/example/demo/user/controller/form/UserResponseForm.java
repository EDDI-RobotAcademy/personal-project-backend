package com.example.demo.user.controller.form;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserResponseForm {
    final private String email;

    public UserResponseForm(String email) {
        this.email = email;
    }
}
