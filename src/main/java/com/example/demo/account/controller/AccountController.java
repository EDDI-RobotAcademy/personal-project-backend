package com.example.demo.account.controller;

import com.example.demo.account.controller.form.AccessRegisterRequestForm;
import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.account.controller.response.LoginResponse;
import com.example.demo.account.controller.response.MyPageResponse;
import com.example.demo.security.jwt.service.AccountResponse;
import com.example.demo.account.service.AccountService;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.security.jwt.service.AccountDetails;
import com.example.demo.security.jwt.subject.TokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    final private AccountService accountService;
    final private JwtProvider jwtProvider;

    @PostMapping("/sign-up")
    public Boolean signUp(@RequestBody AccountRegisterRequestForm form) {
        return accountService.signUp(form.toAccountRegisterRequest());
    }

    @PostMapping("/admin-sign-up")
    public Boolean adminSignUp(@RequestBody AccessRegisterRequestForm form) {
        return accountService.accessSignUp(form.toAccessRegisterRequest());
    }

    @GetMapping("/check-email/{email}")
    public Boolean checkEmail(@PathVariable("email") String email) {
        return accountService.checkEmail(email);
    }

    @PostMapping("/log-in")
    public LoginResponse login(@RequestBody AccountLoginRequestForm form, Long accountId) {
        return accountService.login(form, accountId);
    }

    @PostMapping("/reissue")
    public TokenResponse reissue(@AuthenticationPrincipal AccountDetails accountDetails) throws JsonProcessingException {
        if (accountDetails == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "인증 실패");
        }
        AccountResponse accountResponse = AccountResponse.of(accountDetails.getAccount());
        return jwtProvider.reissueAccessToken(accountResponse);
    }

    @PostMapping("/myPage")
    public MyPageResponse profile(@RequestHeader("Authorization") String accessToken) {
        return accountService.findAccountInfo(accessToken);
    }
}

