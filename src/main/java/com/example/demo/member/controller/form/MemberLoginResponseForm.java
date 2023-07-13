package com.example.demo.member.controller.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
@Getter
@RequiredArgsConstructor
public class MemberLoginResponseForm {
    final private String userToken;
    final private String roleType;
    final private String nickName;
}
