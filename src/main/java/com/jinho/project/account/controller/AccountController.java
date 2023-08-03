package com.jinho.project.account.controller;

import com.jinho.project.account.controller.form.*;
import com.jinho.project.account.entity.Account;
import com.jinho.project.account.service.AccountService;
import com.jinho.project.account.service.request.NormalAccountRegisterRequest;
import com.jinho.project.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    final private RedisService redisService;
    final private AccountService accountService;

    @PostMapping("/normal-register")
    public Boolean normalAccountRegister(@RequestBody NormalAccountRegisterForm requestForm) {
        return accountService.normalAccountRegister(requestForm.toAccountRegisterRequest());

    }

    // 로그인
    @PostMapping("/login")
    public AccountLoginResponseForm accountLogin (@RequestBody AccountLoginRequestForm accountLoginRequestForm){


//        String userToken = accountService.login(accountLoginRequestForm);
        AccountLoginResponseForm accountLoginResponseForm =  accountService.login(accountLoginRequestForm);
        return accountLoginResponseForm;
    }

    @GetMapping("/check-email/{email}")
    public Boolean checkEmail(@PathVariable("email") String email) {
        log.info("check email duplication: " + email);

        return accountService.checkEmailDuplication(email);
    }

    // accountId 반환
    @PostMapping("/return-accountId")
    public Long returnAccountId(@RequestBody ReturnAccountIdRequestForm requestForm) {
        log.info("returnAccountId()");

        String userToken = requestForm.getUserToken();
        Long returnAccountId = accountService.findAccountId(userToken);
        log.info("returnEmail: " + returnAccountId);

        return returnAccountId;
    }



    @PostMapping("/give-info")
    public Account readAccount (@RequestBody AccountReadRequestForm requestForm) {
        log.info("readAccount()");

        return accountService.read(requestForm);
    }
    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public void deleteAccount (@PathVariable("id") Long id) {
        log.info("deleteAccount()");

        accountService.delete(id);
    }

    // 닉네임 변경
    @PutMapping("change-nickname/{id}")
    public Account changeNickname (@PathVariable("id") Long id,
                                  @RequestBody NormalAccountRegisterRequest request) {
        log.info("changeNickname()");

        return accountService.modifyNickname(id, request);
    }

    // 사용자 이름 변경
    @PutMapping("change-username/{id}")
    public Account changeUsername (@PathVariable("id") Long id,
                                  @RequestBody NormalAccountRegisterRequest request) {
        log.info("changeUsername()");
        log.info("id: " + id );
        log.info("request:" + request.getUserName());

        return accountService.modifyUsername(id, request);
    }



}

