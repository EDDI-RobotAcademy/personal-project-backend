package com.example.demo.account.controller;


import com.example.demo.account.controller.form.request.*;
import com.example.demo.account.controller.form.response.AccountLoginResponseForm;
import com.example.demo.account.controller.form.response.AccountPasswordResponseForm;
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
        Account account = accountService.regist(requestForm.toAccount());
        if (account == null) {
            return null;
        }
        return account.getAccountId();
    }

    // 로그인 기능
    @PostMapping("/login")
    public AccountLoginResponseForm login(@RequestBody AccountLoginRequestForm requestForm){
        log.info("login() ");
        AccountLoginResponseForm accountLoginResponseForm = accountService.login(requestForm);

        return accountLoginResponseForm;
    }

    // 이메일 중복 확인 기능
    @GetMapping("/check-email/{email}")
    public Boolean checkEmail(@PathVariable("email") String email){
        log.info("check email : " + email);

        return accountService.checkEmail(email);
    }

    // 모든 계정 리스트 확인 기능
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
    public void accountDelete(@RequestBody AccountUserTokenRequestForm requestForm) {

        log.info("accountDelete()");
        accountService.delete(requestForm);

    }
    
    // 계정 수정 기능
    @PutMapping("/{accountId}")
    public void accountModify(@RequestBody AccountModifyRequestForm requestForm){
        log.info("accountModify()");

        accountService.modify(requestForm);
    }

    // 비밀번호 찾기 기능
    @GetMapping("/password-find")
    public AccountPasswordResponseForm accountPasswordFind(@RequestBody AccountPasswordFindRequestForm requestForm){
        log.info(requestForm.getAccountName() + " 님의 비밀번호 찾기 ");
        AccountPasswordResponseForm accountPasswordResponseForm = accountService.passwordFind(requestForm);

        return accountPasswordResponseForm;
    }

    // 마이페이지 들어갈 때
    @PostMapping("/gomypage")
    public String goMypage(@RequestBody AccountGoMypageForm accountGoMypageForm){
        log.info("accountGoMypageForm()");
        String goMypage_account = accountService.goMypage(accountGoMypageForm);

        return goMypage_account;
    }

    // 내 정보 확인
    @GetMapping("/{accountId}")
    public Account accountInfo(@PathVariable String accountId){
        log.info("accountInfo() ");
        Account accountInfo = accountService.accountInfoList(accountId);
log.info(String.valueOf(accountInfo));
        return accountInfo;
    }

    @GetMapping("/userType-check")
    public String userTypeCheck(AccountUserTokenRequestForm accountUserTokenRequestForm){
        log.info("userTypeCheck()");
        String userType = accountService.userTypeCheck(accountUserTokenRequestForm);

        return userType;
    }

    @PostMapping("/manager/regist")
    public String managerRegist(ManagerRegistRequestForm managerRegistRequestForm){
        log.info("ManagerRegist() ");
        Account managerAccount = accountService.regist(managerRegistRequestForm.toAccount());


        return managerAccount.getEmail();
    }


}
