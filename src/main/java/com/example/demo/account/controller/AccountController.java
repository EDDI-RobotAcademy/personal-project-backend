package com.example.demo.account.controller;


import com.example.demo.account.controller.form.AccountDeleteRequestForm;
import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountModifyRequestForm;
import com.example.demo.account.controller.form.AccountRegistRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    final AccountService accountService;

    // 계정 등록 기능
    @PostMapping("/regist")
    public Long regist(@RequestBody AccountRegistRequestForm requestForm) {
        log.info("regist()");
        Account account = accountService.regist(requestForm);
        if (account == null) {
            return null;
        }
        return account.getId();
    }

    // 로그인 기능
    @PostMapping("/login")
    public String login(@RequestBody AccountLoginRequestForm requestForm){
        log.info("login");
        Account account = accountService.login(requestForm);

        return account.getUserToken();
    }

    // 이메일 중복 확인 기능
    @GetMapping("/check-email/{email}")
    public Boolean checkEmail(@PathVariable("email") String email){
        log.info("check email : " + email);

        return accountService.checkEmail(email);
    }

    // 계정 리스트 확인 기능
    @GetMapping("/list")
    public List<Account> accountList(){
        log.info("  ");
        log.info("AccountList()");

        List<Account> returnedAccountList = accountService.list();

        log.info("returnedAccountList : " + returnedAccountList);

        return returnedAccountList;
    }

    // 계정 삭제 기능
    @DeleteMapping("/delete")
    public void accountDelete(@RequestBody AccountDeleteRequestForm requestForm) {
        log.info(requestForm.getUserToken());
        log.info("accountDelete()");
        accountService.delete(requestForm);

    }
    
    // 계정 수정 기능
    @PutMapping("/modify")
    public void accountModify(@PathVariable("email") String email,
                              @RequestBody AccountModifyRequestForm accountModifyRequestForm){
        log.info("accountModify(): " + accountModifyRequestForm + ", email : " + email);

        accountService.modify(email, accountModifyRequestForm);

    }

}
