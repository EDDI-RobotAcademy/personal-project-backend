package com.jinho.project.account.service;

import com.jinho.project.account.controller.form.AccountLoginRequestForm;
import com.jinho.project.account.entity.Account;
import com.jinho.project.account.entity.AccountRole;
import com.jinho.project.account.entity.Role;
import com.jinho.project.account.repository.*;
import com.jinho.project.account.service.request.NormalAccountRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    final private AccountRepository accountRepository;
    final private AccountRoleRepository accountRoleRepository;
    final private RoleRepository roleRepository;
    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();



    @Override
    public Boolean normalAccountRegister(NormalAccountRegisterRequest request) {

        log.info(request.toString());
        final Optional<Account> maybeAccount = accountRepository.findByEmail(request.getEmail());
        // 이메일 중복 확인
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
    public String login(AccountLoginRequestForm requestForm) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(requestForm.getUserEmail());
        // 이메일 확인 후 비밀번호 검사
        if(maybeAccount.isPresent()) {
            if(requestForm.getPassword().equals(maybeAccount.get().getPassword())) {
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
