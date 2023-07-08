package com.example.demo.account.controller;

import com.example.demo.account.controller.form.AccessRegisterRequestForm;
import com.example.demo.account.controller.form.AccountRegisterRequestForm;
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
    public Boolean checkEmail(@PathVariable("email") String email){
        log.info("check email : " + email);

        return accountService.checkEmail(email);
    }
}
