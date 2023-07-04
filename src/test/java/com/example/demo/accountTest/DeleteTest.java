package com.example.demo.accountTest;

import com.example.demo.account.controller.form.request.AccountUserTokenRequestForm;
import com.example.demo.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class DeleteTest {

    @Autowired
    AccountService accountService;

    @Test
    @DisplayName("로그인이 된 회원의 정보를 삭제합니다.")
    void 회원_정보_삭제(){
        final String userToken = "69236f92-d8b1-4b31-b40e-f2dfb1d56186";

        AccountUserTokenRequestForm accountUserTokenRequestForm = new AccountUserTokenRequestForm(userToken);

        boolean result_delete = accountService.delete(accountUserTokenRequestForm);

        assertTrue(result_delete);
    }
}
