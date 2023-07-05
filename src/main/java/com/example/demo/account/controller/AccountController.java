package com.example.demo.account.controller;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountLoginResponseForm;
import com.example.demo.account.controller.form.AccountModifyRequestForm;
import com.example.demo.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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

    @PostMapping("email-check")
    public Boolean emailCheck(@RequestParam("email") String email){
        return accountService.duplicateCheckEmail(email);
    }

    @PostMapping("nickname-check")
    public Boolean nicknameCheck(@RequestParam("nickname") String nickname){
        return accountService.duplicateCheckNickname(nickname);
    }

    @PostMapping("login")
    public AccountLoginResponseForm login(@RequestBody AccountLoginRequestForm requestForm){
        return accountService.login(requestForm);
    }

    @PutMapping("modify")
    public Account modify(@RequestBody AccountModifyRequestForm requestForm){
        return accountService.modify(requestForm);
    }

    @PostMapping("logout")
    public Boolean logout(@RequestParam("userToken") String userToken){
        return accountService.logout(userToken);
    }

    @DeleteMapping("withdrawal")
    public Boolean withdrawal(@RequestParam("userToken") String userToken){
        return accountService.withdrawal(userToken);
    }
}
