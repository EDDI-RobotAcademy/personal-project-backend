package com.jinho.project.account.controller;

import com.jinho.project.account.controller.form.AccountLoginRequestForm;
import com.jinho.project.account.controller.form.NormalAccountRegisterForm;
import com.jinho.project.account.service.AccountService;
import com.jinho.project.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    public String accountLogin(@RequestBody AccountLoginRequestForm accountLoginRequestForm) {

        String userToken = accountService.login(accountLoginRequestForm);
        Long accountID= accountService.findAccountIdByEmail(accountLoginRequestForm.getUserEmail());
        redisService.setKeyAndValue(userToken,accountID);
        return userToken;
    }

}

