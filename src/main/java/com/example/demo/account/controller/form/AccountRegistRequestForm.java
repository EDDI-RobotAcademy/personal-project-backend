package com.example.demo.account.controller.form;


import com.example.demo.account.entity.Account;

public class AccountRegistRequestForm {
    String email;
    String password;

    public AccountRegistRequestForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Account toAccount() {
        return new Account(this.email, this.password);
    }
}