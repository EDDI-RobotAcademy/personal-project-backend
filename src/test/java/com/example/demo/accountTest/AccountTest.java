package com.example.demo.accountTest;

import com.example.demo.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AccountTest {

    @Autowired
    AccountRepository testAccountRepository;

    @Test
    @DisplayName("사용자 회원 가입")
    void 사용자_회원_가입 () {
        final String email = "test@test.com";
        final String password = "test";
        final String nickname = "abc";

        AccountRegisterRequestForm testRequestForm = new AccountRegisterRequestForm(email, password, nickname);

        Account testAccount = testAccountRepository.save(testRequestForm.toAccount());

        assertEquals(email, testAccount.getAccountId());
        assertEquals(password, testAccount.getPassword());
        assertEquals(nickname, testAccount.getNickname());
    }
}
