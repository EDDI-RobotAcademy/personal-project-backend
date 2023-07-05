package com.example.demo.accountTest;

import com.example.demo.account.controller.form.request.AccountUserTokenRequestForm;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserTypeCheckTest {

    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

    @Test
    @DisplayName("userToken으로 userType을 확인합니다.")
    void 사용자_유형_확인(){
//        final String userToken = "b65b57f2-4482-4065-a2be-a16efc49bd69";
        final String userToken = "d6ff5767-470c-4e56-af7c-22f6fc545172";

        AccountUserTokenRequestForm accountUserTokenRequestForm = new AccountUserTokenRequestForm(userToken);

        String userType = accountService.userTypeCheck(accountUserTokenRequestForm);
        String dbUserType = accountRepository.findByUserToken(userToken).get().getUserType();

        System.out.println(userType);
        assertEquals(userType,dbUserType);


    }
}
