package com.example.demo.account.service;

import com.example.demo.account.entity.Account;
import com.example.demo.account.entity.AccountRole;
import com.example.demo.account.entity.Role;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.repository.AccountRoleRepository;
import com.example.demo.account.repository.RoleRepository;
import com.example.demo.account.service.request.BusinessAccountRegisterRequest;
import com.example.demo.account.service.request.NormalAccountRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    final private AccountRepository accountRepository;
    final private RoleRepository roleRepository;
    final private AccountRoleRepository accountRoleRepository;

    @Override
    public Boolean normalAccountRegister(NormalAccountRegisterRequest request) {
        final Optional<Account> maybeAccount = accountRepository.findByEmail(request.getEmail());
        // 중복 이메일 확인
        if (maybeAccount.isPresent()) {
            return false;
        }

        // 계정 생성
        final Account account = accountRepository.save(request.toAccount());

        // 회원 타입 부여
        final Role role = roleRepository.findByRoleType(request.getRoleType()).get();
        final AccountRole accountRole = new AccountRole(role, account);
        accountRoleRepository.save(accountRole);

        return true;
    }

    @Override
    public Boolean businessAccountRegister(BusinessAccountRegisterRequest request) {
        final Optional<Account> maybeAccount =
                accountRepository.findByEmail(request.getEmail());
        // 중복 이메일 확인
        if (maybeAccount.isPresent()) {
            return false;
        }

        final Long businessNumber = request.getBusinessNumber();

        // 중복 사업자 번호 확인
        final Optional<AccountRole> maybeAccountRole =
                accountRoleRepository.findByBusinessNumber(businessNumber);

        if(maybeAccountRole.isPresent()) {
            return false;
        }

        // 계정 생성
        final Account account = accountRepository.save(request.toAccount());

        // 회원 타입 부여
        final Role role = roleRepository.findByRoleType(request.getRoleType()).get();

        final AccountRole accountRole = new AccountRole(role, account, businessNumber);
        accountRoleRepository.save(accountRole);

        return true;
    }

    @Override
    public Boolean checkEmailDuplication(String email) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);

        if (maybeAccount.isPresent()) {
            return false;
        } else {
            return true;
        }    }
}
