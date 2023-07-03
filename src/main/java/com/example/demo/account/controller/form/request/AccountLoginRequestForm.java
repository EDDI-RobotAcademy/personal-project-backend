package com.example.demo.account.controller.form.request;


import com.example.demo.account.entity.Account;

public class AccountLoginRequestForm {

    final private String email;
    final private String password;

    public AccountLoginRequestForm(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public Account toAccount(){
        return new Account(email, password);
    }
}