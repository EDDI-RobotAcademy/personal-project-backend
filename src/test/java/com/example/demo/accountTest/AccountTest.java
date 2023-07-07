package com.example.demo.accountTest;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountModifyRequestForm;
import com.example.demo.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.service.AccountService;
import com.example.demo.account.service.AccountServiceImpl;
import com.example.demo.authentication.redis.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class AccountTest {

    @Autowired
    AccountRepository testAccountRepository;

    @Autowired
    RedisService testRedisService;

    @Mock
    private AccountRepository mockAccountRepository;

    @BeforeEach
    public void setup () throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Autowired
    AccountService testAccountService;

    @Autowired
    BCryptPasswordEncoder testEncoder;

    @Test
    @DisplayName("사용자 회원 가입")
    void 사용자_회원_가입() {
        final String email = "testing@test.com";
        final String password = "testing";
        final String nickname = "testing";

        AccountRegisterRequestForm testRequestForm = new AccountRegisterRequestForm(email, password, nickname);

        Account testAccount = testAccountRepository.save(testRequestForm.toAccount(testEncoder.encode(password)));

        assertEquals(email, testAccount.getEmail());
        assertNotEquals(password, testAccount.getPassword());
        assertEquals(nickname, testAccount.getNickname());
        assertTrue(testEncoder.matches(password, testAccount.getPassword()));
    }

    @Test
    @DisplayName("회원 가입")
    void 회원_가입(){
        final String email = "test1@test.com";
        final String password = "test1";
        final String nickname = "test1";

        AccountRegisterRequestForm requestForm = new AccountRegisterRequestForm(email, password, nickname);

        String encodePassword = testEncoder.encode(requestForm.getPassword());

        final Account account = requestForm.toAccount(encodePassword);

//        when(mockAccountRepository.save(account)).thenReturn(new Account(email, encodePassword, nickname));
        when(mockAccountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account savedAccount = invocation.getArgument(0);
            return new Account(savedAccount.getEmail(), savedAccount.getPassword(), savedAccount.getNickname());
        });

        final AccountServiceImpl sut = new AccountServiceImpl(mockAccountRepository, testRedisService, testEncoder);
        final Account actual = sut.register(requestForm);

        verify(mockAccountRepository, times(1)).save(any(Account.class));

        assertEquals(actual.getEmail(), account.getEmail());
        assertNotEquals(actual.getPassword(), encodePassword);
        assertEquals(actual.getNickname(), account.getNickname());
    }

    @Test
    @DisplayName("이메일 중복 체크")
    void 이메일_중복_체크() {
        final String email = "test@test.com";

        Optional<Account> maybeAccount = testAccountRepository.findByEmail(email);

        Account account = maybeAccount.get();

        assertEquals(email, account.getEmail());
    }

    @Test
    @DisplayName("닉네임 중복 체크")
    void 닉네임_중복_체크() {
        final String nickname = "abc";

        Optional<Account> maybeAccount = testAccountRepository.findByNickname(nickname);

        Account account = maybeAccount.get();

        assertEquals(nickname, account.getNickname());
    }

    @Test
    @DisplayName("없는 이메일 로그인")
    void 없는_이메일_로그인() {
        final String email = "testing@test.com";
        final String password = "test";

        AccountLoginRequestForm requestForm = new AccountLoginRequestForm(email, password);

        assertNull(testAccountService.login(requestForm));
    }

    @Test
    @DisplayName("틀린 비밀번호 로그인")
    void 틀린_비밀번호_로그인() {
        final String email = "test@test.com";
        final String password = "testing";

        AccountLoginRequestForm requestForm = new AccountLoginRequestForm(email, password);

        assertNull(testAccountService.login(requestForm));
    }

    @Test
    @DisplayName("정상 로그인")
    void 로그인() {
        final String email = "testing@test.com";
        final String password = "testing";

        AccountLoginRequestForm requestForm = new AccountLoginRequestForm(email, password);
        String accessToken = testAccountService.login(requestForm).getAccessToken();
        System.out.println(accessToken);
        assertNotNull(accessToken);

    }

//    @Test
//    @DisplayName("닉네임 수정")
//    void 닉네임_수정(){
//        final String modifyNickname = "qwe";
//        final String userToken = "2c2d1983-e8b6-41bc-a9f6-5262ebe49c21";
//
//        AccountModifyRequestForm requestForm = new AccountModifyRequestForm(modifyNickname, null);
//
//        assertEquals(modifyNickname, testAccountService.modify(requestForm).getNickname());
//    }
//
//    @Test
//    @DisplayName("비밀번호 수정")
//    void 비밀번호_수정(){
//        final String modifyPassword = "testing";
//        final String userToken = "2c2d1983-e8b6-41bc-a9f6-5262ebe49c21";
//
//        AccountModifyRequestForm requestForm = new AccountModifyRequestForm(userToken, null, modifyPassword);
//
//        assertEquals(modifyPassword, testAccountService.modify(requestForm).getPassword());
//    }
//
//    @Test
//    @DisplayName("닉네임_비밀번호 수정")
//    void 닉네임_비밀번호_수정(){
//        final String modifyNickname = "zxc";
//        final String modifyPassword = "asd";
//        final String userToken = "2c2d1983-e8b6-41bc-a9f6-5262ebe49c21";
//
//        AccountModifyRequestForm requestForm = new AccountModifyRequestForm(userToken, modifyNickname, modifyPassword);
//
//        assertEquals(modifyNickname, testAccountService.modify(requestForm).getNickname());
//        assertEquals(modifyPassword, testAccountService.modify(requestForm).getPassword());
//    }

//    @Test
//    @DisplayName("로그아웃")
//    void 로그아웃(){
//        final String userToken = "4afd1e0d-bcd3-4639-91a1-28ffe8de0193";
//
//        assertTrue(testAccountService.logout(userToken));
//        assertNull(testRedisService.getValueByKey(userToken));
//    }
//
//    @Test
//    @DisplayName("회원 탈퇴")
//    void 회원_탈퇴(){
//        final String userToken = "97b91f2f-0765-43e7-8bec-7185aada1de0";
//
//        assertTrue(testAccountService.withdrawal(userToken));
//    }
}
