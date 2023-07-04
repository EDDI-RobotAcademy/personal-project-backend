package com.example.demo.accountTest;


import com.example.demo.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CheckEmailTest {

    @Autowired
    AccountService accountService;

    @Test
    @DisplayName("DB에 있는 이메일 중복확인을 합니다.")
    void 존재하는_이메일_중복_확인(){

        final String email = "test@test.com";

        Boolean checkEmailInfo = accountService.checkEmail(email);

        assertFalse(checkEmailInfo);
    }
    @Test
    @DisplayName("DB에 없는 이메일 중복확인을 합니다.")
    void 존재하지_않는_이메일_중복_확인(){

        final String email = "abracadabra@test.com";

        Boolean checkEmailInfo = accountService.checkEmail(email);

        assertTrue(checkEmailInfo);

    }
}
