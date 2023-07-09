package com.example.demo.account.controller;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountModifyRequestForm;
import com.example.demo.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.service.AccountService;
import com.example.demo.authentication.jwt.TokenInfo;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    final private AccountService accountService;

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
    public TokenInfo login(@RequestBody AccountLoginRequestForm requestForm, HttpServletResponse response){
        TokenInfo tokenInfo = accountService.login(requestForm);
        Cookie accessCookie = new Cookie("AccessToken", tokenInfo.getAccessToken());
        accessCookie.setPath("/");
        accessCookie.setMaxAge(60*60);
        response.addCookie(accessCookie);

        Cookie refreshCookie = new Cookie("RefreshToken", tokenInfo.getRefreshToken());
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(60*60);
        response.addCookie(refreshCookie);

        return tokenInfo;
    }

    @PostMapping("/login2")
    public TokenInfo login2(@RequestBody AccountLoginRequestForm requestForm){
        return accountService.login(requestForm);
    }

    @PutMapping("/modify")
    public Account modify(@RequestBody AccountModifyRequestForm requestForm){
        return accountService.modify(requestForm);
    }

    @PostMapping("/logout")
    public Boolean logout(@RequestParam("userToken") String userToken){
        return accountService.logout(userToken);
    }

    @DeleteMapping("/withdrawal")
    public Boolean withdrawal(@RequestParam("userToken") String userToken){
        return accountService.withdrawal(userToken);
    }
}
