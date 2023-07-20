package com.example.demo.account.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class MyPageResponse {

    private String email;

    private String name;

    private String phoneNumber;

}
