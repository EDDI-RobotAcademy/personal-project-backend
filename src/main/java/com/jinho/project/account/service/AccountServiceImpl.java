package com.jinho.project.account.service;

import com.jinho.project.account.controller.form.*;
import com.jinho.project.account.entity.Account;
import com.jinho.project.account.entity.AccountRole;
import com.jinho.project.account.entity.Role;
import com.jinho.project.account.entity.RoleType;
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
    public AccountLoginResponseForm login(AccountLoginRequestForm requestForm) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(requestForm.getUserEmail());
        // 이메일 확인 후 비밀번호 검사
        if(maybeAccount.isPresent()) {
            if(requestForm.getPassword().equals(maybeAccount.get().getPassword())) {
                // 맞으면 해당 계정 가져와서 토큰 부여 후 반환

                final Account account = maybeAccount.get();
                final Long id = account.getId();
                final String userToken = UUID.randomUUID().toString();
                final String nickname = account.getNickname();
                final String email = account.getEmail();
                final String userName = account.getUserName();
                userTokenRepository.save(userToken, account.getId());
                AccountLoginResponseForm accountLoginResponseForm = new AccountLoginResponseForm(id,email, userName,nickname, userToken);

                return accountLoginResponseForm;
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
    public Long findAccountId(String userToken) {
        final Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);

        log.info("accountId: " + accountId);

        return accountId;
    }

    @Override
    public String findAccountNickname(Long accountId) {
        Optional<Account> maybeAccount = accountRepository.findById(accountId);
        if(maybeAccount.isPresent()) {
            String nickname = maybeAccount.get().getNickname();
            return nickname;
        } return null;
    }

    @Override
    public String findAccountEmail(Long accountId) {
        Optional<Account> maybeAccount = accountRepository.findById(accountId);
        if(maybeAccount.isPresent()) {
            String email = maybeAccount.get().getEmail();
            return email;
        }
        return null;
    }

    @Override
    public String lookupUserName(Long accountId) {
        Optional<Account> maybeAccount = accountRepository.findById(accountId);
        if (maybeAccount.isPresent()) {
            final Account account = maybeAccount.get();
            final String userName = account.getUserName();
            return userName;
        }
        return null;
    }

    @Override
    public String lookupNickName(Long accountId) {
        Optional<Account> maybeAccount = accountRepository.findById(accountId);
        if (maybeAccount.isPresent()) {
            final Account account = maybeAccount.get();
            final String nickname = account.getNickname();
            return nickname;
        }
        return null;
    }

    @Override
    public Account read(AccountReadRequestForm requestForm) {
        final String userToken = requestForm.getUserToken();
        final Long accountId = userTokenRepository.findAccountIdByUserToken(userToken);

        Optional <Account> maybeAccount = accountRepository.findById(accountId);
        if (maybeAccount.isPresent()) {
            return maybeAccount.get();
        }

        return null;
    }

    @Override
    public Account modifyNickname(Long id, NormalAccountRegisterRequest request) {
        Optional<Account> maybeAccount = accountRepository.findById(id);

        if (maybeAccount.isEmpty()) {
            log.info("존재하지 않는 회원입니다.");
            return null;
        }

        Account account = maybeAccount.get();
        account.setNickname(request.getNickname());

        accountRepository.save(account);

        return accountRepository.save(account);
    }

    @Override
    public Account modifyUsername(Long id, NormalAccountRegisterRequest request) {
        Optional<Account> maybeAccount = accountRepository.findById(id);

        if (maybeAccount.isEmpty()) {
            log.info("존재하지 않는 회원입니다.");
            return null;
        }

        Account account = maybeAccount.get();
        account.setUserName(request.getUserName());

        accountRepository.save(account);

        return accountRepository.save(account);
    }

    @Override
    public void delete(Long id) {
        accountRoleRepository.deleteAllByAccountId(id);
        accountRepository.deleteById(id);
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
}
