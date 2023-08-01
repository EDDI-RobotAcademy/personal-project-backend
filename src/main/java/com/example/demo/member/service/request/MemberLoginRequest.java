package com.example.demo.member.service.request;

import jakarta.persistence.GeneratedValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberLoginRequest {
    final private String email;
    final private String password;

}
