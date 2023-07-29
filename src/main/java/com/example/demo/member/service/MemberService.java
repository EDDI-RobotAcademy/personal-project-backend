package com.example.demo.member.service;


import com.example.demo.member.controller.form.MemberLoginResponseForm;
import com.example.demo.member.controller.form.MemberRequestForm;
import com.example.demo.member.entity.Member;
import com.example.demo.member.service.request.MemberLoginRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;

public interface MemberService {

    Boolean register(MemberRequestForm requestForm);

    Boolean checkNickname(String nickname);

    MemberLoginResponseForm login(MemberLoginRequest request);

    Boolean checkEmail(String email);

    Member signUpKakao(String email, String nickname);

    Boolean checkMember(Long memberId, HttpHeaders headers);

}
