package com.example.demo.accountTest;

import com.example.demo.account.controller.form.AccountRegisterRequestForm;
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
    @DisplayName("회원 가입")
    void 회원_가입 () {
        final String email = "test@test.com";
        final String password = "test";
        final String name = "이름";
        final String phoneNumber = "000-0000-0000";
        final RoleType roleType = NORMAL;

        AccountRegisterRequestForm registerForm = new AccountRegisterRequestForm(email, password, name, phoneNumber, roleType);
        boolean isSuccess = accountService.signUp(registerForm.toAccountRegisterRequest());

        assertTrue(isSuccess == true);
    }
}
