package com.example.demo.account.controller.form.request;


import com.example.demo.account.entity.Account;

public class AccountRegistRequestForm {
    String email;
    String password;
    String accountName;
    String accountBirth;
    String accountPhone;
    String accountAddress;

    public AccountRegistRequestForm(String email, String password, String accountName, String accountBirth, String accountPhone, String accountAddress) {
        this.email = email;
        this.password = password;
        this.accountName = accountName;
        this.accountBirth = accountBirth;
        this.accountPhone = accountPhone;
        this.accountAddress = accountAddress;
    }

    public Account toAccount() {
        return new Account(email,password,accountName,accountBirth,accountPhone,accountAddress);
    }

}