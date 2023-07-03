package com.example.demo.accountTest;

import com.example.demo.account.controller.form.request.AccountPasswordFindRequestForm;
import com.example.demo.account.controller.form.response.AccountPasswordResponseForm;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class FindPasswordTest {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("올바른 이메일, 이름, 생년월일 을 입력해서 비밀번호를 찾습니다.")
    void 정상적인_비밀번호_찾기() {
        final String email = "test@test.com";
        final String accountName = "김대완";
        final String accountBirth = "2012-04-18";

        AccountPasswordFindRequestForm accountPasswordFindRequestForm = new AccountPasswordFindRequestForm(email, accountName, accountBirth);

        AccountPasswordResponseForm resultFindPW = accountService.passwordFind(accountPasswordFindRequestForm);

        System.out.println(resultFindPW.getPassword());
        System.out.println(resultFindPW.getPasswordFindStatus());

        assertEquals(resultFindPW.getPassword(), accountRepository.findByEmail(email).get().getPassword());

    }

    @Test
    @DisplayName("이메일이 틀렸을 때의 비밀번호 찾기")
    void 틀린_이메일() {
        final String email = "asdf@test.com";
        final String accountName = "김대완";
        final String accountBirth = "2012-04-18";

        AccountPasswordFindRequestForm accountPasswordFindRequestForm = new AccountPasswordFindRequestForm(email, accountName, accountBirth);

        AccountPasswordResponseForm resultFindPW = accountService.passwordFind(accountPasswordFindRequestForm);

        System.out.println(resultFindPW.getPassword());
        System.out.println(resultFindPW.getPasswordFindStatus());

        assertNotEquals(resultFindPW.getPassword(), accountRepository.findByEmail(email).get().getPassword());
    }

    @Test
    @DisplayName("이름이 틀렸을 때의 비밀번호 찾기")
    void 틀린_이름() {
        final String email = "test@test.com";
        final String accountName = "똥멍청이";
        final String accountBirth = "2012-04-18";

        AccountPasswordFindRequestForm accountPasswordFindRequestForm = new AccountPasswordFindRequestForm(email, accountName, accountBirth);

        AccountPasswordResponseForm resultFindPW = accountService.passwordFind(accountPasswordFindRequestForm);

        System.out.println(resultFindPW.getPassword());
        System.out.println(resultFindPW.getPasswordFindStatus());

        assertNotEquals(resultFindPW.getPassword(), accountRepository.findByEmail(email).get().getPassword());
    }
    @Test
    @DisplayName("생년월일이 틀렸을 때의 비밀번호 찾기")
    void 틀린_생년월일() {
        final String email = "test@test.com";
        final String accountName = "김대완";
        final String accountBirth = "1999-04-18";

        AccountPasswordFindRequestForm accountPasswordFindRequestForm = new AccountPasswordFindRequestForm(email, accountName, accountBirth);

        AccountPasswordResponseForm resultFindPW = accountService.passwordFind(accountPasswordFindRequestForm);

        System.out.println(resultFindPW.getPassword());
        System.out.println(resultFindPW.getPasswordFindStatus());

        assertNotEquals(resultFindPW.getPassword(), accountRepository.findByEmail(email).get().getPassword());
    }
}
