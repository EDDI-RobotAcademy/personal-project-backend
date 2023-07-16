package com.example.demo.account.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@RequiredArgsConstructor
public class MyPageRequestForm {

    final private String email;

    final private String name;

    final private String phoneNumber;


}
