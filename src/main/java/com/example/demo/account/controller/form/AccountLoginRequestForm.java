package com.example.demo.account.controller.form;


import com.example.demo.account.entity.Account;

public class AccountLoginRequestForm {
    public AccountLoginRequestForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    final private String email;
    final private String password;

    public Account toAccount(){
        return new Account(email, password);
    }
}