package com.example.demo.accountTest;

import com.example.demo.account.controller.form.request.AccountModifyRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ModifyTest {

    @Autowired
    AccountService accountService;
    @Autowired
    AccountRepository accountRepository;

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
}
