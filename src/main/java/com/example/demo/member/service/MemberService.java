package com.example.demo.member.service;


import com.example.demo.member.controller.form.MemberLoginResponseForm;
import com.example.demo.member.controller.form.MemberRequestForm;
import com.example.demo.member.service.request.MemberLoginRequest;

public interface MemberService {

    Boolean register(MemberRequestForm requestForm);

    Boolean checkNickName(String nickName);

    MemberLoginResponseForm login(MemberLoginRequest request);

}
