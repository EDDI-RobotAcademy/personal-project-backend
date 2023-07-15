package com.example.demo.domain.account.service;

import com.example.demo.authentication.jwt.JwtTokenUtil;
import com.example.demo.authentication.jwt.TokenInfo;
import com.example.demo.authentication.redis.RedisService;
import com.example.demo.domain.account.controller.form.*;
import com.example.demo.domain.account.entity.Account;
import com.example.demo.domain.account.repository.AccountRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            return null;
        }
        Account account = maybeAccount.get();

        if(!encoder.matches(requestForm.getPassword(), account.getPassword())){
            System.out.println("비밀번호 틀림");
            return null;
        }

        long expireTimeMs = 1000 * 60 * 60;     // Token 유효 시간 = 60분

        TokenInfo tokenInfo = JwtTokenUtil.createToken(account.getEmail(), secretKey, expireTimeMs);
        redisService.setKeyAndValue(tokenInfo.getRefreshToken(), account.getId());

        return new AccountLoginResponseForm(tokenInfo, account.getNickname());
    }

    @Override
    public boolean modify(AccountModifyRequestForm requestForm, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String email = null;

        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                String token = cookie.getValue();
                email = JwtTokenUtil.getEmail(token, secretKey);
                break;
            }
        }

        Optional<Account> maybeAccount = accountRepository.findByEmail(email);

        if(maybeAccount.isEmpty()){
            return false;
        }
        Account account = maybeAccount.get();

        if(requestForm.getNickname() == null && requestForm.getPassword() == null){
            return false;
        }

        if(requestForm.getNickname() != null){
            account.setNickname(requestForm.getNickname());
        }
        if(requestForm.getPassword() != null){
            account.setPassword(encoder.encode(requestForm.getPassword()));
        }
        accountRepository.save(account);
        return true;
    }

    @Override
    public Boolean logout(HttpServletResponse response){
        if(response.getStatus() == 200){
            return true;
        }
        return false;
    }

    @Override
    public Boolean withdrawal(String email) {
        accountRepository.deleteByEmail(email);
        return true;
    }

    @Override
    public Account getLoginAccountByEmail(String email) {
        if(email == null) return null;

        Optional<Account> optionalAccount = accountRepository.findByEmail(email);
        if(optionalAccount.isEmpty()) return null;

        return optionalAccount.get();
    }

    @Override
    public boolean duplicateCheckPassword(AccountPasswordCheckRequestForm requestForm, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String email = null;

        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                String token = cookie.getValue();
                email = JwtTokenUtil.getEmail(token, secretKey);
                break;
            }
        }

        Optional<Account> maybeAccount = accountRepository.findByEmail(email);
        if(maybeAccount.isEmpty()){
            return false;
        }
        Account account = maybeAccount.get();

        if(!encoder.matches(requestForm.getPassword(), account.getPassword())){
            log.info("비밀번호 틀림");
            return false;
        }

        return true;
    }
}
