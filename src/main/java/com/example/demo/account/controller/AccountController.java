package com.example.demo.account.controller;

import com.example.demo.account.controller.form.business.BusinessAccountRegisterForm;
import com.example.demo.account.controller.form.normal.NormalAccountRegisterForm;
import com.example.demo.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    final private AccountService accountService;

    // 개인 회원
    @PostMapping("/normal-register")
    public Boolean normalAccountRegister (@RequestBody NormalAccountRegisterForm registerForm) {
        return accountService.normalAccountRegister(registerForm.toAccountRegisterRequest());
    }

    // 사업자 회원
    @PostMapping("/business-register")
    public Boolean businessAccountRegister (@RequestBody BusinessAccountRegisterForm requestForm) {
        return accountService.businessAccountRegister(requestForm.toAccountRegisterRequest());
    }

    @GetMapping("/check-email/{email}")
    public Boolean checkEmail(@PathVariable("email") String email) {

        return accountService.checkEmailDuplication(email);
    }
}
