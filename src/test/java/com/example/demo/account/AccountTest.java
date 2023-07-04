package com.example.demo.account;

import com.example.demo.account.controller.form.AccountSignUpRequestForm;
import com.example.demo.account.entity.RoleType;
import com.example.demo.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.example.demo.account.entity.RoleType.NORMAL;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AccountTest {

    @Autowired
    private AccountService accountService;

    @Test
    @DisplayName("일반 회원 가입")
    void 일반_회원_가입 () {
        final String email = "test@test.com";
        final String password = "test";
        final String name = "이름";
        final String phoneNumber = "000-0000-0000";
        final RoleType roleType = NORMAL;


        AccountSignUpRequestForm form = new AccountSignUpRequestForm(email, password, name, phoneNumber,roleType);
        Boolean isSuccess = accountService.signUp(form.toAccountSignUpRequest());

        assertTrue(isSuccess);
    }
}
