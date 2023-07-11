package com.example.demo.security.jwt.subject;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

    final private String accessToken;

    final private String refreshToken;
}
