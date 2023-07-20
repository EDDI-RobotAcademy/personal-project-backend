package com.example.demo.domain.account.controller;

import com.example.demo.authentication.jwt.JwtTokenUtil;
import com.example.demo.domain.account.controller.form.*;
import com.example.demo.domain.account.entity.Account;
import com.example.demo.domain.account.service.AccountService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    final private AccountService accountService;
    final private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/register")
    public Account accountRegister (@RequestBody AccountRegisterRequestForm requestForm) {
        return accountService.register(requestForm);
    }

    @GetMapping("/email-check")
    public Boolean emailCheck(@RequestParam("email") String email){
        return accountService.duplicateCheckEmail(email);
    }

    @GetMapping("/nickname-check")
    public Boolean nicknameCheck(@RequestParam("nickname") String nickname){
        return accountService.duplicateCheckNickname(nickname);
    }

    @PostMapping("/login")
    public String login(@RequestBody AccountLoginRequestForm requestForm, HttpServletResponse response){
        AccountLoginResponseForm loginResponse = accountService.login(requestForm);

        Cookie accessCookie = jwtTokenUtil.generateCookie("AccessToken", loginResponse.getTokenInfo().getAccessToken(), 24*60*60);
        response.addCookie(accessCookie);

        Cookie refreshCookie = jwtTokenUtil.generateCookie("RefreshToken", loginResponse.getTokenInfo().getRefreshToken(), 24*60*60);
        response.addCookie(refreshCookie);

        return loginResponse.getNickname();
    }

    @PostMapping("/modify")
    public boolean modify(@RequestBody AccountModifyRequestForm requestForm, HttpServletRequest request){
        return accountService.modify(requestForm, request);
    }

    @PostMapping("/logout")
    public Boolean logout(HttpServletRequest request, HttpServletResponse response){
        jwtTokenUtil.deleteLoginInfo(request);
        
        Cookie accessCookie = jwtTokenUtil.generateCookie("AccessToken", null, 0);
        response.addCookie(accessCookie);

        Cookie refreshCookie = jwtTokenUtil.generateCookie("RefreshToken", null, 0);
        response.addCookie(refreshCookie);

        return accountService.logout(response);
    }

    @DeleteMapping("/withdraw")
    public Boolean withdrawal(HttpServletRequest request, HttpServletResponse response){
        String email = jwtTokenUtil.getEmailFromCookie(request);

        Cookie accessCookie = jwtTokenUtil.generateCookie("AccessToken", null, 0);
        response.addCookie(accessCookie);

        Cookie refreshCookie = jwtTokenUtil.generateCookie("RefreshToken", null, 0);
        response.addCookie(refreshCookie);

        return accountService.withdrawal(email);
    }

    @PostMapping("/password-check")
    public boolean passwordCheck(@RequestBody AccountPasswordCheckRequestForm requestForm, HttpServletRequest request){

        return accountService.duplicateCheckPassword(requestForm, request);
    }
}
