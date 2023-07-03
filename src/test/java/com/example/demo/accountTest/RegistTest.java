package com.example.demo.accountTest;

import com.example.demo.account.controller.form.request.AccountRegistRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RegistTest {
    @Autowired
    AccountService accountService;

    @Test
    @DisplayName("회원 가입")
    void account_regist(){
        final String email = "test@test.com";
        final String password = "111";
        final String accountName = "김대완";
        final String accountBirth = "2012-04-18";
        final String accountPhone = "010-2345-6789";
        final String accountAddress = "서울시 서울역 2번 출구";

        AccountRegistRequestForm accountRegistRequestForm =
                new AccountRegistRequestForm(email,password,accountName,accountBirth,accountPhone,accountAddress);
        Account account = accountService.regist(accountRegistRequestForm);

        assertEquals(email, account.getEmail());
        assertEquals(password, account.getPassword());
        assertEquals(accountName, account.getAccountName());
        assertEquals(accountBirth, account.getAccountBirth());
        assertEquals(accountPhone, account.getAccountPhone());
        assertEquals(accountAddress, account.getAccountAddress());




    }
}
