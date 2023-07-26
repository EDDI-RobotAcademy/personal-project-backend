package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.entity.RoleType;
import com.example.demo.account.service.request.BusinessAccountRegisterRequest;
import com.example.demo.account.service.request.NormalAccountRegisterRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

public interface AccountService {
    Boolean normalAccountRegister(NormalAccountRegisterRequest request);
    Boolean businessAccountRegister(BusinessAccountRegisterRequest request);
    Boolean checkEmailDuplication(String email);
    String login(AccountLoginRequestForm accountLoginRequestForm);
    RoleType lookup(String userToken);
    Long findAccountId(String userToken);
    Boolean businessCheck(Long businessId);
    String returnEmail(String userToken);
    // 메일 내용 작성
    MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException;
    // 랜덤 인증 코드 전송
    String createKey();
    String sendSimpleMessage(String email) throws MessagingException, UnsupportedEncodingException;
}
