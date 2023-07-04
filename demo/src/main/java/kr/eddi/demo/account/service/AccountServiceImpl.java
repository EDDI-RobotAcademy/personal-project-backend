package kr.eddi.demo.account.service;

import kr.eddi.demo.account.controller.form.AccountLoginRequestForm;
import kr.eddi.demo.account.entity.Account;
import kr.eddi.demo.account.repository.AccountRepository;
import kr.eddi.demo.account.repository.UserTokenRepository;
import kr.eddi.demo.account.repository.UserTokenRepositoryImpl;
import kr.eddi.demo.account.service.request.AccountRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    final private AccountRepository accountRepository;
    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();
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

    @Override
    public String login(AccountLoginRequestForm accountLoginRequestForm) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(accountLoginRequestForm.getEmail());
        // 이메일 확인 후 비밀번호 검사
        if(maybeAccount.isPresent()) {
            if(accountLoginRequestForm.getPassword().equals(maybeAccount.get().getPassword())) {
                // 맞으면 해당 계정 가져와서 토큰 부여 후 반환
                final Account account = maybeAccount.get();
                final String userToken = UUID.randomUUID().toString();
                userTokenRepository.save(userToken, account.getId());
                return userToken;
            }
        }

        return null;
    }

    @Override
    public Long findAccountIdByEmail(String email) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);
        if (maybeAccount.isPresent()){
            return maybeAccount.get().getId();
        }
        return null;
    }
}
