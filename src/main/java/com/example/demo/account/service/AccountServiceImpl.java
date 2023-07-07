package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountModifyRequestForm;
import com.example.demo.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.authentication.jwt.JwtTokenUtil;
import com.example.demo.authentication.jwt.TokenInfo;
import com.example.demo.authentication.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    final private AccountRepository accountRepository;
    final private RedisService redisService;
    final private BCryptPasswordEncoder encoder;



    @Value("${jwt.secret}")
    private String secretKey;

    @Override
    public Account register(AccountRegisterRequestForm requestForm) {
        Account account = accountRepository.save(requestForm.toAccount(encoder.encode(requestForm.getPassword())));

        return account;
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
    public TokenInfo login(AccountLoginRequestForm requestForm) {
        Optional<Account> maybeAccount =
                accountRepository.findByEmail(requestForm.getEmail());

        if(maybeAccount.isEmpty()){
            System.out.println("이메일 틀림");
            return null;
        }
        Account account = maybeAccount.get();

        if(!encoder.matches(requestForm.getPassword(), account.getPassword())){
            System.out.println("비밀번호 틀림");
            return null;
        }

        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분

        return JwtTokenUtil.createToken(account.getEmail(), secretKey, expireTimeMs);
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

    @Override
    public Account getLoginAccountById(Long id) {
        if(id == null) return null;

        Optional<Account> optionalAccount = accountRepository.findById(id);
        if(optionalAccount.isEmpty()) return null;

        return optionalAccount.get();
    }

    @Override
    public Account getLoginAccountByEmail(String email) {
        if(email == null) return null;

        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if(optionalAccount.isEmpty()) return null;

        return optionalAccount.get();
    }
}
