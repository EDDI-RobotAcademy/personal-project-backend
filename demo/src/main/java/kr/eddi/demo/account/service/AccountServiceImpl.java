package kr.eddi.demo.account.service;

import kr.eddi.demo.account.entity.Account;
import kr.eddi.demo.account.repository.AccountRepository;
import kr.eddi.demo.account.service.request.AccountRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    final private AccountRepository accountRepository;
    @Override
    public Boolean register(AccountRequest accountRequest) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(accountRequest.getEmail());

        if (maybeAccount.isEmpty()){
            Account account = accountRequest.toAccount();
            accountRepository.save(account);
            return true;
        }
        return false;
    }
}
