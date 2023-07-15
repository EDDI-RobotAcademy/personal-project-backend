package com.example.demo.domain.account.controller;

import com.example.demo.domain.account.controller.form.*;
import com.example.demo.domain.account.entity.Account;
import com.example.demo.domain.account.service.AccountService;
import com.example.demo.authentication.jwt.JwtTokenUtil;
import com.example.demo.authentication.jwt.TokenInfo;
import com.example.demo.authentication.redis.RedisService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    final private AccountService accountService;
    final private JwtTokenUtil jwtTokenUtil;
    final private RedisService redisService;

    @Value("${jwt.secret}")
    private String secretKey;

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

    @PutMapping("/modify")
    public Account modify(@RequestBody AccountModifyRequestForm requestForm, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String email = null;

        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                String token = cookie.getValue();
                email = JwtTokenUtil.getEmail(token, secretKey);
                break;
            }
        }
        return accountService.modify(email, requestForm);
    }

    @PostMapping("/logout")
    public Boolean logout(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();

        for(Cookie cookie : cookies){
            if(cookie.getName().equals("AccessToken")){
                String token = cookie.getValue();

                Date exp = JwtTokenUtil.getExp(token, secretKey);
                Date date = new Date();

                redisService.registBlackList(token, exp.getTime()-date.getTime());
            }
            if(cookie.getName().equals("RefreshToken")){
                String token = cookie.getValue();
                redisService.deleteByKey(token);
            }
        }

        Cookie accessCookie = jwtTokenUtil.generateCookie("AccessToken", null, 0);
        response.addCookie(accessCookie);

        Cookie refreshCookie = jwtTokenUtil.generateCookie("RefreshToken", null, 0);
        response.addCookie(refreshCookie);

        return accountService.logout(response);
    }

    @DeleteMapping("/withdrawal")
    public Boolean withdrawal(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String email = null;

        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                String token = cookie.getValue();
                email = JwtTokenUtil.getEmail(token, secretKey);
            }
            if(cookie.getName().equals("RefreshToken")){
                String token = cookie.getValue();
                redisService.deleteByKey(token);
            }
        }
        return accountService.withdrawal(email);
    }

    @PostMapping("/password-check")
    public boolean passwordCheck(@RequestBody AccountPasswordCheckRequestForm requestForm, HttpServletRequest request){

        return accountService.duplicateCheckPassword(requestForm, request);
    }
}
