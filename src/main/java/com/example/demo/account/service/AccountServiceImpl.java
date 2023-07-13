package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.entity.AccountRole;
import com.example.demo.account.entity.Role;
import com.example.demo.account.entity.RoleType;
import com.example.demo.account.repository.*;
import com.example.demo.account.service.request.BusinessAccountRegisterRequest;
import com.example.demo.account.service.request.NormalAccountRegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.example.demo.account.entity.RoleType.BUSINESS;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    final private AccountRepository accountRepository;
    final private RoleRepository roleRepository;
    final private AccountRoleRepository accountRoleRepository;
    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();

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
        }
    }

    @Override
    public String login(AccountLoginRequestForm requestForm) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(requestForm.getEmail());

        if(maybeAccount.isPresent()) {
            final Account account = maybeAccount.get();

            if(requestForm.getPassword().equals(maybeAccount.get().getPassword())) {
                final String userToken = UUID.randomUUID().toString();
                userTokenRepository.save(userToken, account.getId());
                return userToken;
            }
        }

        return "";
    }

    @Override
    public RoleType lookup(String userToken) {
        final Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);

        final Optional<Account> maybeAccount = accountRepository.findById(accountId);

        if (maybeAccount.isEmpty()) {
            return null;
        }

        final Account account = maybeAccount.get();
        final Role role = accountRoleRepository.findRoleByAccount(account);

        log.info("roleType: " + role.getRoleType());
        return role.getRoleType();
    }

    @Override
    public Long findAccountId(String userToken) {
        final Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);

        log.info("accountId: " + accountId);

        return accountId;
    }

    @Override
    public Boolean businessCheck(Long accountId) {
        Optional<AccountRole> maybeAccountRole = accountRoleRepository.findByAccountIdWithRole(accountId);

        log.info("가져온 어카운트 롤 ID: " + String.valueOf(maybeAccountRole.get().getId()));
        log.info("어카운트 롤의 롤타입: " + String.valueOf(maybeAccountRole.get().getRole().getRoleType()));

        Role role= maybeAccountRole.get().getRole();
        if (role.getRoleType().equals(BUSINESS)){
            return true;
        }

        return false;
    }
}
