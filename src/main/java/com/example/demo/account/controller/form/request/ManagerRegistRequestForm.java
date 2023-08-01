package com.example.demo.account.controller.form.request;

import com.example.demo.account.entity.Account;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
public class ManagerRegistRequestForm {
    final private String email;
    final private String password;
    final private String userType;

    public ManagerRegistRequestForm(String email, String password) {
        this.email = email;
        this.password = password;
        this.userType = "Manager";
    }

    public Account toAccount(){
        return new Account(email,password,userType);
    }
}
