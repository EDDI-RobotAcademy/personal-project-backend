package com.example.demo.accountTest;

import com.example.demo.account.controller.form.request.AccountGoMypageForm;
import com.example.demo.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class GoMypageTest {
    @Autowired
    AccountService accountService;

    @Test
    @DisplayName("비밀번호가 맞으면 마이페이지로 이동합니다.")
    void 비밀번호_확인(){

        final String userToken = "4ebe577f-be49-49bf-ad59-246ade4a6583";
        final String password = "777";

        AccountGoMypageForm accountGoMypageForm = new AccountGoMypageForm(userToken,password);
        Boolean goMyPage_info = accountService.goMypage(accountGoMypageForm);

        assertTrue(goMyPage_info);

    }

    @Test
    @DisplayName("비밀번호가 틀렸을 때")
    void 틀린_비밀번호_확인(){

        final String userToken = "4ebe577f-be49-49bf-ad59-246ade4a6583";
        final String password = "4444";

        AccountGoMypageForm accountGoMypageForm = new AccountGoMypageForm(userToken,password);
        Boolean goMyPage_info = accountService.goMypage(accountGoMypageForm);

        assertFalse(goMyPage_info);
    }
}
