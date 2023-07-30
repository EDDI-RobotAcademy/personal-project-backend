package kr.eddi.demo.account.service;

import kr.eddi.demo.account.controller.form.AccountLoginRequestForm;
import kr.eddi.demo.account.controller.form.PasswordCheckForm;
import kr.eddi.demo.account.entity.Account;
import kr.eddi.demo.account.service.request.AccountRequest;

public interface AccountService {

    Boolean register(AccountRequest accountRequest);

    String login(AccountLoginRequestForm accountLoginRequestForm);

    Long findAccountIdByEmail(String email);

    String findNicknameByAccountId(Long accountID);

    Boolean checkPassword(PasswordCheckForm form);


    Account findAccountByUsertoken(Long valueByKey);
}
