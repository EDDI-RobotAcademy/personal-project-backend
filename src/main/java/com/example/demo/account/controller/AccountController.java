package com.example.demo.account.controller;

import com.example.demo.account.controller.form.AccessRegisterRequestForm;
import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.service.AccountService;
//import com.example.demo.redis.RedisService;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.security.jwt.service.AccountDetails;
import com.example.demo.security.jwt.subject.TokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.Token;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    final private AccountService accountService;
    final private JwtProvider jwtProvider;
//    final private RedisService redisService;

    @PostMapping("/sign-up")
    public Boolean signUp(@RequestBody AccountRegisterRequestForm form) {
        log.info("signUp(): " + form);

        return accountService.signUp(form.toAccountRegisterRequest());
    }

    @PostMapping("/admin-sign-up")
    public Boolean adminSignUp(@RequestBody AccessRegisterRequestForm form) {
        log.info("admin: " + form);

        return accountService.accessSignUp(form.toAccessRegisterRequest());
    }

    @GetMapping("/check-email/{email}")
    public Boolean checkEmail(@PathVariable("email") String email) {
        log.info("check email : " + email);

        return accountService.checkEmail(email);
    }

    @PostMapping("/log-in")
    public TokenResponse  login(@RequestBody AccountLoginRequestForm form) {
        log.info("로그인: " + form);

        return accountService.login(form);
    }

    @GetMapping("/log-in/test")
    public String test() {
        log.info("test!");
        return "test";
    }

}

