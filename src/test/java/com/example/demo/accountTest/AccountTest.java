package com.example.demo.accountTest;

import com.example.demo.account.controller.form.request.*;
import com.example.demo.account.controller.form.response.AccountPasswordResponseForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AccountTest {
    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("회원 가입")
    void account_regist(){
//        final String email = "test@test.com";
//        final String password = "111";
//        final String accountName = "김대완";
//        final String accountBirth = "2012-04-18";
//        final String accountPhone = "010-2345-6789";
//        final String accountAddress = "서울시 서울역 2번 출구";

        final String email = "1234@1234.com";
        final String password = "777";
        final String accountName = "김똥개";
        final String accountBirth = "2002-08-28";
        final String accountPhone = "010-7777-7777";
        final String accountAddress = "부산시 부산역 9번 출구";
        AccountRegistRequestForm accountRegistRequestForm =
                new AccountRegistRequestForm(email,password,accountName,accountBirth,accountPhone,accountAddress);
        Account account = accountService.regist(accountRegistRequestForm);

        assertEquals(email, account.getEmail());
        assertEquals(password, account.getPassword());
        assertEquals(accountName, account.getAccountName());
        assertEquals(accountBirth, account.getAccountBirth());
        assertEquals(accountPhone, account.getAccountPhone());
        assertEquals(accountAddress, account.getAccountAddress());

    }

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
    @Test
    @DisplayName("회원의 정보를 수정 합니다.")
    void 회원_정보_수정(){
        final String userToken = "4ebe577f-be49-49bf-ad59-246ade4a6583";
        final String password = "777";
        final String account_address = "인천광역시 강화군 길상면 전등사로 37-41";
        final String account_birth = "1996-10-07";
        final String account_name = "멋쟁이";
        final String account_phone = "010-9876-5432";
        AccountModifyRequestForm accountModifyRequestForm = new AccountModifyRequestForm(userToken,password,account_name,account_birth,account_phone,account_address);
        accountService.modify(accountModifyRequestForm);
        Account account_info = accountRepository.findByUserToken(userToken).get();

        assertEquals(accountModifyRequestForm.getPassword(),account_info.getPassword());
        assertEquals(accountModifyRequestForm.getAccountAddress(),account_info.getAccountAddress());
        assertEquals(accountModifyRequestForm.getAccountBirth(),account_info.getAccountBirth());
        assertEquals(accountModifyRequestForm.getAccountName(),account_info.getAccountName());
        assertEquals(accountModifyRequestForm.getAccountPhone(),account_info.getAccountPhone());

    }

    @Test
    @DisplayName("로그인이 된 회원의 정보를 삭제합니다.")
    void 회원_정보_삭제(){
        final String userToken = "69236f92-d8b1-4b31-b40e-f2dfb1d56186";
        AccountUserTokenRequestForm accountUserTokenRequestForm = new AccountUserTokenRequestForm(userToken);
        boolean result_delete = accountService.delete(accountUserTokenRequestForm);

        assertTrue(result_delete);
    }

    @Test
    @DisplayName("전체 회원 목록 확인")
    void 회원_목록_확인(){
        List<Account> AccountList = accountService.list();
        for (Account account: AccountList){
            System.out.println("===============");
            System.out.println(account.getAccountId());
            System.out.println(account.getEmail());

            assertTrue(account != null);
            assertTrue(account.getAccountId() != null);
            assertTrue(account.getEmail() != null);

        }

    }

    @Test
    @DisplayName("로그인된 고객의 정보를 확인합니다.")
    void 회원_정보_확인 (){
        final String userToken = "3f145a95-b00e-4660-b808-da0137feff7b";
        AccountUserTokenRequestForm accountUserTokenRequestForm = new AccountUserTokenRequestForm(userToken);
        Account accountInfo = accountService.accountInfoList(accountUserTokenRequestForm);
        Account dbAccount = accountRepository.findByUserToken(userToken).get();
        System.out.println("이메일 : " + accountInfo.getEmail());
        System.out.println("비밀번호 : " + accountInfo.getPassword());
        System.out.println("이름 : " + accountInfo.getAccountName());
        System.out.println("생년월일 : " + accountInfo.getAccountBirth());
        System.out.println("폰번호 : " + accountInfo.getAccountPhone());
        System.out.println("주소 : " + accountInfo.getAccountAddress());

        assertEquals(accountInfo.getEmail(),dbAccount.getEmail());
        assertEquals(accountInfo.getPassword(),dbAccount.getPassword());
        assertEquals(accountInfo.getAccountName(),dbAccount.getAccountName());
        assertEquals(accountInfo.getAccountBirth(),dbAccount.getAccountBirth());
        assertEquals(accountInfo.getAccountPhone(),dbAccount.getAccountPhone());
        assertEquals(accountInfo.getAccountAddress(),dbAccount.getAccountAddress());
    }

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

    @Test
    @DisplayName("userToken으로 userType을 확인합니다.")
    void 사용자_유형_확인(){
        final String userToken = "d6ff5767-470c-4e56-af7c-22f6fc545172";
        AccountUserTokenRequestForm accountUserTokenRequestForm = new AccountUserTokenRequestForm(userToken);
        String userType = accountService.userTypeCheck(accountUserTokenRequestForm);
        String dbUserType = accountRepository.findByUserToken(userToken).get().getUserType();
        System.out.println(userType);

        assertEquals(userType,dbUserType);
    }

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
