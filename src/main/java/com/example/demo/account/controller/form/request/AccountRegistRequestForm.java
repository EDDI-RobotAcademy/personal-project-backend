package com.example.demo.account.controller.form.request;


import com.example.demo.account.entity.Account;

public class AccountRegistRequestForm {
    final private String email;
    final private String password;
    final private String accountName;
    final private String accountBirth;
    final private String accountPhone;
    final private String accountAddress;
    final private String userType;


    public AccountRegistRequestForm(String email, String password, String accountName, String accountBirth, String accountPhone, String accountAddress) {
        this.email = email;
        this.password = password;
        this.accountName = accountName;
        this.accountBirth = accountBirth;
        this.accountPhone = accountPhone;
        this.accountAddress = accountAddress;
        this.userType = "Account";
    }

    public Account toAccount() {
        return new Account(email,password,accountName,accountBirth,accountPhone,accountAddress,userType);
    }

}