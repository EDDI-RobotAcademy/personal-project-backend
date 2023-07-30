package com.example.demo.account.service;

import com.example.demo.account.controller.form.modify.AddressModifyForm;
import com.example.demo.account.controller.form.modify.PasswordModifyForm;
import com.example.demo.account.controller.form.request.AccountLoginRequestForm;
import com.example.demo.account.controller.form.request.AccountReadRequestForm;
import com.example.demo.account.controller.form.request.CheckPasswordRequestForm;
import com.example.demo.account.controller.form.modify.NicknameModifyForm;
import com.example.demo.account.entity.Account;
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
    Account read(AccountReadRequestForm requestForm);
    String returnPassword(CheckPasswordRequestForm requestForm);
    String modifyNickname(Long id, NicknameModifyForm modifyForm);
    String modifyAddress(Long id, AddressModifyForm modifyForm);
    String modifyPassword(Long id, PasswordModifyForm modifyForm);
    void delete(Long id);
    String returnNickname(Long id);
    RoleType returnRoleType(Long id);
    Boolean checkBusinessNumberDuplication(String businessNumber);
    Boolean checkNickNameDuplication(String nickName);
}
