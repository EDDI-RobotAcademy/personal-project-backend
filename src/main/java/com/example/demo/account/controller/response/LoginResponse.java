package com.example.demo.account.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class LoginResponse {

    private Long accountId;

    private String accessToken;

    private String refreshToken;
}
