package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountRegisterRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    final private AccountRepository accountRepository;

    @Override
    public Account register(AccountRegisterRequestForm requestForm) {

        return accountRepository.save(requestForm.toAccount());
    }
}
