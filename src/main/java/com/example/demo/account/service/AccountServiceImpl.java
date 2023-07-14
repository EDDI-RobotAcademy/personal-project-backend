package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountLoginResponseForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.entity.AccountRole;
import com.example.demo.account.entity.Role;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.repository.AccountRoleRepository;
import com.example.demo.account.repository.RoleRepository;
import com.example.demo.account.service.request.AccountRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    final private AccountRepository accountRepository;
    final private AccountRoleRepository accountRoleRepository;
    final private RoleRepository roleRepository;

    @Override
    public Boolean checkEmailDuplication(String email) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);

        if (maybeAccount.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean signUp(AccountRegisterRequest request) {
        Account account = accountRepository.save(request.toAccount());

//        final Role role = roleRepository.findByRoleType(request.getRoleType()).get();
//        final AccountRole accountRole = new AccountRole(account, role);
//        accountRoleRepository.save(accountRole);

//        account.setAccountRole(accountRole);

        return true;
    }

    @Override
    public AccountLoginResponseForm login(AccountLoginRequestForm requestForm){
        final Optional<Account> memberAccount =
                accountRepository.findByEmail(requestForm.getEmail());
        if (memberAccount.isEmpty()) {
            log.info("로그인 실패!");
            return new AccountLoginResponseForm(null, null);
        }
        Account account = memberAccount.get();
        if (account.getPassword().equals(requestForm.getPassword())) {
            log.info("로그인 성공!: " + account.getEmail());
            return new AccountLoginResponseForm(UUID.randomUUID(), account.getEmail());
        }
        return new AccountLoginResponseForm(null, null);
    }
}