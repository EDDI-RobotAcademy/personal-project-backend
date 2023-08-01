package com.example.demo.account.controller.form.request;


import com.example.demo.account.entity.Account;
import lombok.Getter;

@Getter
public class AccountLoginRequestForm {

    private String email;
    private String password;

    public AccountLoginRequestForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public Account toAccount(){
        return new Account(email, password);
    }
}