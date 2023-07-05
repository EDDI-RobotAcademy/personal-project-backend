package com.example.demo.accountTest;

import com.example.demo.account.controller.form.request.AccountLoginRequestForm;
import com.example.demo.account.controller.form.response.AccountLoginResponseForm;
import com.example.demo.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class LoginTest {

    @Autowired
    AccountService accountService;

    @Test
    @DisplayName("매니저가 로그인을 합니다.")
    void 매니저_로그인(){
        final String email = "manager";
        final String password ="11";

        AccountLoginRequestForm accountLoginRequestForm = new AccountLoginRequestForm(email,password);
        AccountLoginResponseForm accountLoginResponseForm = accountService.login(accountLoginRequestForm);

        System.out.println(accountLoginResponseForm.getUserToken());
        System.out.println(accountLoginResponseForm.getLoginStatus());

        assertNotNull(accountLoginResponseForm.getLoginStatus());
    }

    @Test
    @DisplayName("정상적인 회원이 로그인 시도")
    void 정상적인_회원이_로그인을_시도(){
        final String email = "test@test.com";
        final String password = "111";


        AccountLoginRequestForm accountLoginRequestForm = new AccountLoginRequestForm(email,password);
        AccountLoginResponseForm accountLoginResponseForm = accountService.login(accountLoginRequestForm);

        System.out.println(accountLoginResponseForm.getUserToken());
        System.out.println(accountLoginResponseForm.getLoginStatus());

        assertNotNull(accountLoginResponseForm.getLoginStatus());
    }

    @Test
    @DisplayName("Email이 틀린 회원이 로그인 시도")
    void Email이_틀린_회원이_로그인_시도(){
        final String email = "asdf@asdf.com";
        final String password = "111";


        AccountLoginRequestForm accountLoginRequestForm = new AccountLoginRequestForm(email,password);
        AccountLoginResponseForm accountLoginResponseForm = accountService.login(accountLoginRequestForm);

        System.out.println(accountLoginResponseForm.getUserToken());
        System.out.println(accountLoginResponseForm.getLoginStatus());

        assertNotNull(accountLoginResponseForm.getLoginStatus());
    }

    @Test
    @DisplayName("비밀번호가 틀린 회원이 로그인 시도")
    void PW가_틀린_회원이_로그인_시도(){
        final String email = "test@test.com";
        final String password = "11111";


        AccountLoginRequestForm accountLoginRequestForm = new AccountLoginRequestForm(email,password);
        AccountLoginResponseForm accountLoginResponseForm = accountService.login(accountLoginRequestForm);

        System.out.println(accountLoginResponseForm.getUserToken());
        System.out.println(accountLoginResponseForm.getLoginStatus());

        assertNotNull(accountLoginResponseForm.getLoginStatus());
    }
}
