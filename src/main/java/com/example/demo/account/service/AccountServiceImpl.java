package com.example.demo.account.service;

import com.example.demo.account.authentication.redis.RedisService;
import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountLoginResponseForm;
import com.example.demo.account.controller.form.AccountModifyRequestForm;
import com.example.demo.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    final private AccountRepository accountRepository;
    final private RedisService redisService;
    final private BCryptPasswordEncoder encoder;

    @Override
    public Account register(AccountRegisterRequestForm requestForm) {

        return accountRepository.save(requestForm.toAccount(encoder.encode(requestForm.getPassword())));
    }

    @Override
    public Boolean duplicateCheckEmail(String email) {
        final Optional<Account> maybeAccount = accountRepository.findByEmail(email);
        if(maybeAccount.isPresent()){
            return false;
        }
        return true;
    }

    @Override
    public Boolean duplicateCheckNickname(String nickname) {
        final Optional<Account> maybeAccount = accountRepository.findByNickname(nickname);
        if(maybeAccount.isPresent()){
            return false;
        }
        return true;
    }

    @Override
    public AccountLoginResponseForm login(AccountLoginRequestForm requestForm) {
        Optional<Account> maybeAccount =
                accountRepository.findByEmail(requestForm.getEmail());

        if(maybeAccount.isEmpty()){
            System.out.println("이메일 틀림");
            return new AccountLoginResponseForm(null);
        }
        Account account = maybeAccount.get();

        if(!encoder.matches(requestForm.getPassword(), account.getPassword())){
            System.out.println("비밀번호 틀림");
            return new AccountLoginResponseForm(null);
        }
        final String userToken = UUID.randomUUID().toString();
        redisService.setKeyAndValue(userToken, account.getAccountId());

        return new AccountLoginResponseForm(userToken);
    }

    @Override
    public Account modify(AccountModifyRequestForm requestForm) {
        Optional<Account> maybeAccount = accountRepository.findById(redisService.getValueByKey(requestForm.getUserToken()));

        if(maybeAccount.isEmpty()){
            return null;
        }
        Account account = maybeAccount.get();

        if(requestForm.getNickname()!=null){
            account.setNickname(requestForm.getNickname());
        }
        if(requestForm.getPassword()!=null){
            account.setPassword(requestForm.getPassword());
        }

        return accountRepository.save(account);
    }

    @Override
    public Boolean logout(String userToken){
        Long accountId = redisService.getValueByKey(userToken);

        if(accountId==null){
            return false;
        }
        redisService.deleteByKey(userToken);
        return true;
    }

    @Override
    public Boolean withdrawal(String userToken) {
        Long accountId = redisService.getValueByKey(userToken);

        if(accountId==null){
            System.out.println("accountId = " + accountId);
            return false;
        }
        redisService.deleteByKey(userToken);
        accountRepository.deleteById(accountId);
        return true;
    }
}
