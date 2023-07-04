package kr.eddi.demo.account.controller;

import kr.eddi.demo.account.controller.form.AccountLoginRequestForm;
import kr.eddi.demo.account.controller.form.AccountRegisterForm;
import kr.eddi.demo.account.service.AccountService;
import kr.eddi.demo.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/account")
@Slf4j
@RequiredArgsConstructor
@RestController
public class AccountController {

   final private AccountService accountService;
    final private RedisService redisService;

    @PostMapping("/register")
    public Boolean accountRegister(@RequestBody AccountRegisterForm accountRegisterForm){
        log.info("register(): "+accountRegisterForm);
        return accountService.register(accountRegisterForm.toAccountRequest());
    }
    @PostMapping("/login")
    public String accountLogin(@RequestBody AccountLoginRequestForm accountLoginRequestForm) {

        String userToken = accountService.login(accountLoginRequestForm);
        Long accountID= accountService.findAccountIdByEmail(accountLoginRequestForm.getEmail());
        redisService.setKeyAndValue(userToken,accountID);
        return userToken;
    }
}
