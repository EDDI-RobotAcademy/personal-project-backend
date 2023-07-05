package com.example.demo.member.service;


import com.example.demo.member.controller.form.MemberRequestForm;

public interface MemberService {

    Boolean register(MemberRequestForm requestForm);

    Boolean checkNickName(String nickName);

}
