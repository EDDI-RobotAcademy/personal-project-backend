package com.example.demo.account.controller;

import com.example.demo.account.controller.form.modify.AddressModifyForm;
import com.example.demo.account.controller.form.modify.NicknameModifyForm;
import com.example.demo.account.controller.form.modify.PasswordModifyForm;
import com.example.demo.account.controller.form.request.*;
import com.example.demo.account.entity.Account;
import com.example.demo.account.entity.RoleType;
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

    // 개인 회원 회원 가입
    @PostMapping("/normal-register")
    public Boolean normalAccountRegister (@RequestBody NormalAccountRegisterForm registerForm) {
        return accountService.normalAccountRegister(registerForm.toAccountRegisterRequest());
    }

    // 사업자 회원 회원 가입
    @PostMapping("/business-register")
    public Boolean businessAccountRegister (@RequestBody BusinessAccountRegisterForm requestForm) {
        return accountService.businessAccountRegister(requestForm.toAccountRegisterRequest());
    }

    // 이메일 중복체크
    @GetMapping("/check-email/{email}")
    public Boolean checkEmail (@PathVariable("email") String email) {
        log.info("ckeckEmail: " + email);

        return accountService.checkEmailDuplication(email);
    }

    // 사업자번호 중복체크
    @GetMapping("/check-businessNumber/{businessNumber}")
    public Boolean checkBusinessNumber (@PathVariable("businessNumber") String businessNumber) {
        log.info("checkBusinessNumber: " + businessNumber);

        return accountService.checkBusinessNumberDuplication(businessNumber);
    }

    // 닉네임 중복체크
    @GetMapping("/check-nickname/{nickName}")
    public Boolean checkNickName (@PathVariable("nickName") String nickName) {
        log.info("checkNickName: " + nickName);

        return accountService.checkNickNameDuplication(nickName);
    }

    // 로그인
    @PostMapping("/login")
    public String accountLogin (@RequestBody AccountLoginRequestForm accountLoginRequestForm){

        String userToken = accountService.login(accountLoginRequestForm);

        return userToken;
    }

    // 사업자 회원 확인
    @PostMapping("/businessCheck")
    public Boolean isBusiness(@RequestBody BusinessCheckRequestForm requestForm){

        String userToken = requestForm.getUserToken();
        Long businessId = accountService.findAccountId(userToken);
        log.info("businessId: "+businessId);

        if (businessId==null){
            return false;
        }

        Boolean isBusinessMan= accountService.businessCheck(businessId);
        return isBusinessMan;
    }

    // 이메일 반환
    @PostMapping("/return-email")
    public String returnEmail(@RequestBody ReturnEmailRequestForm requestForm) {
        log.info("returnEmail()");

        String userToken = requestForm.getUserToken();
        String returnEmail = accountService.returnEmail(userToken);
        log.info("returnEmail: " + returnEmail);

        return returnEmail;
    }

    // accountId 반환
    @PostMapping("/return-accountId")
    public Long returnAccountId(@RequestBody ReturnAccountIdRequestForm requestForm) {
        log.info("returnAccountId()");

        String userToken = requestForm.getUserToken();
        Long returnAccountId = accountService.findAccountId(userToken);
        log.info("returnEmail: " + returnAccountId);

        return returnAccountId;
    }

    // 이메일 인증
    @ResponseBody
    @PostMapping("mail-confirm/{email}")
    String mailConfirm(@PathVariable("email") String email) throws Exception {

        String code = accountService.sendSimpleMessage(email);
        System.out.println("인증코드 : " + code);
        return code;
    }

    // 회원 정보
    @PostMapping("/give-info")
    public Account readAccount (@RequestBody AccountReadRequestForm requestForm) {
        log.info("readAccount()");

        return accountService.read(requestForm);
    }

    // 비밀번호 반환
    @PostMapping("/check-password")
    public String returnPassword (@RequestBody CheckPasswordRequestForm requestForm) {
        log.info("returnPassword()");

        return accountService.returnPassword(requestForm);
    }

    // 닉네임 변경
    @PutMapping("change-nickname/{id}")
    public String changeNickname (@PathVariable("id") Long id,
                                  @RequestBody NicknameModifyForm modifyForm) {
        log.info("changeNickname()");

        return accountService.modifyNickname(id, modifyForm);
    }

    // 주소 변경
    @PutMapping("change-address/{id}")
    public String changeAddress (@PathVariable("id") Long id,
                                  @RequestBody AddressModifyForm modifyForm) {
        log.info("changeAddress()");

        return accountService.modifyAddress(id, modifyForm);
    }

    // 비밀번호 변경
    @PutMapping("change-password/{id}")
    public String changePassword (@PathVariable("id") Long id,
                                  @RequestBody PasswordModifyForm modifyForm) {
        log.info("changePassword()");

        return accountService.modifyPassword(id, modifyForm);
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public void deleteAccount (@PathVariable("id") Long id) {
        log.info("deleteAccount()");

        accountService.delete(id);
    }

    // 닉네임 반환
    @GetMapping("/return-nickname/{id}")
    public String returnNickname (@PathVariable("id") Long id) {
        log.info("returnNickname()");

        return accountService.returnNickname(id);
    }

    // 회원 유형 반환
    @GetMapping("/return-roleType/{id}")
    public RoleType returnRoleType (@PathVariable("id") Long id) {
        log.info("returnRoleType()");

        return accountService.returnRoleType(id);
    }
}
