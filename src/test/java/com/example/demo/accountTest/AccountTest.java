package com.example.demo.accountTest;

import com.example.demo.account.authentication.redis.RedisService;
import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountModifyRequestForm;
import com.example.demo.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountTest {

    @Autowired
    AccountRepository testAccountRepository;

    @Autowired
    RedisService testRedisService;

    @Autowired
    AccountService testAccountService;

    @Test
    @DisplayName("사용자 회원 가입")
    void 사용자_회원_가입 () {
        final String email = "testing@test.com";
        final String password = "testing";
        final String nickname = "testing";

        AccountRegisterRequestForm testRequestForm = new AccountRegisterRequestForm(email, password, nickname);

        Account testAccount = testAccountRepository.save(testRequestForm.toAccount());

        assertEquals(email, testAccount.getAccountId());
        assertEquals(password, testAccount.getPassword());
        assertEquals(nickname, testAccount.getNickname());
    }

    @Test
    @DisplayName("이메일 중복 체크")
    void 이메일_중복_체크(){
        final String email = "test@test.com";

        Optional<Account> maybeAccount = testAccountRepository.findByEmail(email);

        Account account = maybeAccount.get();

        assertEquals(email, account.getEmail());
    }

    @Test
    @DisplayName("닉네임 중복 체크")
    void 닉네임_중복_체크(){
        final String nickname = "abc";

        Optional<Account> maybeAccount = testAccountRepository.findByNickname(nickname);

        Account account = maybeAccount.get();

        assertEquals(nickname, account.getNickname());
    }

    @Test
    @DisplayName("없는 이메일 로그인")
    void 없는_이메일_로그인(){
        final String email = "testing@test.com";
        final String password = "test";

        AccountLoginRequestForm requestForm = new AccountLoginRequestForm(email, password);

        assertNull(testAccountService.login(requestForm).getUserToken());
    }

    @Test
    @DisplayName("틀린 비밀번호 로그인")
    void 틀린_비밀번호_로그인(){
        final String email = "test@test.com";
        final String password = "testing";

        AccountLoginRequestForm requestForm = new AccountLoginRequestForm(email, password);

        assertNull(testAccountService.login(requestForm).getUserToken());
    }

    @Test
    @DisplayName("정상 로그인")
    void 로그인(){
        final String email = "test@test.com";
        final String password = "test";

        AccountLoginRequestForm requestForm = new AccountLoginRequestForm(email, password);

        assertNotNull(testAccountService.login(requestForm).getUserToken());
    }

    @Test
    @DisplayName("닉네임 수정")
    void 닉네임_수정(){
        final String modifyNickname = "qwe";
        final String userToken = "2c2d1983-e8b6-41bc-a9f6-5262ebe49c21";

        AccountModifyRequestForm requestForm = new AccountModifyRequestForm(userToken, modifyNickname, null);

        assertEquals(modifyNickname, testAccountService.modify(requestForm).getNickname());
    }

    @Test
    @DisplayName("비밀번호 수정")
    void 비밀번호_수정(){
        final String modifyPassword = "testing";
        final String userToken = "2c2d1983-e8b6-41bc-a9f6-5262ebe49c21";

        AccountModifyRequestForm requestForm = new AccountModifyRequestForm(userToken, null, modifyPassword);

        assertEquals(modifyPassword, testAccountService.modify(requestForm).getPassword());
    }

    @Test
    @DisplayName("닉네임_비밀번호 수정")
    void 닉네임_비밀번호_수정(){
        final String modifyNickname = "zxc";
        final String modifyPassword = "asd";
        final String userToken = "2c2d1983-e8b6-41bc-a9f6-5262ebe49c21";

        AccountModifyRequestForm requestForm = new AccountModifyRequestForm(userToken, modifyNickname, modifyPassword);

        assertEquals(modifyNickname, testAccountService.modify(requestForm).getNickname());
        assertEquals(modifyPassword, testAccountService.modify(requestForm).getPassword());
    }
}
