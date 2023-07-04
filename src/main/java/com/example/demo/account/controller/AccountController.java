package com.example.demo.account.controller;

import com.example.demo.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {
    final private AccountService accountService;

    @PostMapping("/register")
    public Account accountRegister (@RequestBody AccountRegisterRequestForm requestForm) {
        return accountService.register(requestForm);
    }

    @PostMapping("email-check")
    public Boolean emailCheck(@Param("email") String email){
        return accountService.duplicateCheckEmail(email);
    }
}
