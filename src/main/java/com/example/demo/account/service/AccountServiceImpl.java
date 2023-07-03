package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountRegisterForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.service.request.AccountRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    final private AccountRepository accountRepository;
    @Override
    public Boolean register(AccountRegisterRequest registerRequest) {
        accountRepository.save(registerRequest.toAccount());
        return true;
    }

    @Override
    public String findAccountNicknameByEmail(String email) {
        if (email == null) {
            return null;
        }

        final Optional<Account> account = accountRepository.findByEmail(email);

        if (account.isEmpty()) {
            return null;
        }

        return account.get().getNickname();
    }
}
