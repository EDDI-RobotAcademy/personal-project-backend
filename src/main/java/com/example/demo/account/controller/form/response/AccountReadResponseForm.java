package com.example.demo.account.controller.form.response;

import com.example.demo.account.entity.Account;
import com.example.demo.review.entity.Review;
import lombok.Getter;

@Getter
public class AccountReadResponseForm {

    final private Long id;
    final private String email;
    final private String password;
    final private String userName;
    final private String gender;
    final private String birth;
    final private String nickName;
    final private String address;

    public AccountReadResponseForm(Account account) {
        this.id = account.getId();
        this.email = account.getEmail();
        this.password = account.getPassword();
        this.userName = account.getUserName();
        this.gender = account.getGender();
        this.birth = account.getBirth();
        this.nickName = account.getNickName();
        this.address = account.getAddress();
    }
}
