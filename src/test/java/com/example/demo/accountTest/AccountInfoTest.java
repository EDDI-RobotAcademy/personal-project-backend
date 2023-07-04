package com.example.demo.accountTest;

import com.example.demo.account.controller.form.request.AccountUserTokenRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AccountInfoTest {

    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("로그인된 고객의 정보를 확인합니다.")
    void 회원_정보_확인 (){
        final String userToken = "3f145a95-b00e-4660-b808-da0137feff7b";

        AccountUserTokenRequestForm accountUserTokenRequestForm = new AccountUserTokenRequestForm(userToken);

        Account accountInfo = accountService.accountInfoList(accountUserTokenRequestForm);
        Account dbAccount = accountRepository.findByUserToken(userToken).get();

        System.out.println("이메일 : " + accountInfo.getEmail());
        System.out.println("비밀번호 : " + accountInfo.getPassword());
        System.out.println("이름 : " + accountInfo.getAccountName());
        System.out.println("생년월일 : " + accountInfo.getAccountBirth());
        System.out.println("폰번호 : " + accountInfo.getAccountPhone());
        System.out.println("주소 : " + accountInfo.getAccountAddress());

        assertEquals(accountInfo.getEmail(),dbAccount.getEmail());
        assertEquals(accountInfo.getPassword(),dbAccount.getPassword());
        assertEquals(accountInfo.getAccountName(),dbAccount.getAccountName());
        assertEquals(accountInfo.getAccountBirth(),dbAccount.getAccountBirth());
        assertEquals(accountInfo.getAccountPhone(),dbAccount.getAccountPhone());
        assertEquals(accountInfo.getAccountAddress(),dbAccount.getAccountAddress());






    }

}
