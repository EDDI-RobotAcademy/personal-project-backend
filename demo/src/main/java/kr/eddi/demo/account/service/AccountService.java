package kr.eddi.demo.account.service;

import kr.eddi.demo.account.entity.Account;
import kr.eddi.demo.account.service.request.AccountRequest;

public interface AccountService {

    Boolean register(AccountRequest accountRequest);
}
