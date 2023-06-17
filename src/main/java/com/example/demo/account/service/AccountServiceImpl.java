package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountDeleteRequestForm;
import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.form.AccountModifyRequestForm;
import com.example.demo.account.controller.form.AccountRegistRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    final AccountRepository accountRepository;

    // 계정 등록 기능
    @Override
    public Account regist(AccountRegistRequestForm requestForm) {
        Account account = requestForm.toAccount();

        Optional<Account> maybeAccount = accountRepository.findByEmail(account.getEmail());
        if(maybeAccount.isPresent()) {
            return null;
        }
        Account savedAccount = accountRepository.save(account);
        System.out.println(savedAccount);
        return savedAccount;
    }

    // 로그인 기능
    @Override
    public Account login(AccountLoginRequestForm requestForm) {
        Account account = requestForm.toAccount();
        Optional<Account> maybeAccount = accountRepository.findByEmail(account.getEmail());

        if(maybeAccount.isEmpty()){
            return null;
        }
        Account savedAccount = maybeAccount.get();
        if (savedAccount.getPassword().equals(account.getPassword())) {
            savedAccount.setUserToken(UUID.randomUUID().toString());
            return accountRepository.save(savedAccount);
        }
        return null;
    }

    // 계정 리스트 확인 기능
    @Override
    public Boolean checkEmail(String email) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);

        if (maybeAccount.isPresent()){
            return false;
        }else{
            return true;
        }

    }

    @Override
    public List<Account> list() {
        return accountRepository.findAll(Sort.by(Sort.Direction.DESC, "email"));
    }

    // 계정 삭제 기능
    @Override
    public Boolean delete(AccountDeleteRequestForm requestForm) {
        Account account = requestForm.toAccount();
        Optional<Account> maybeAccount = accountRepository.findByUserToken(account.getUserToken());

        if (maybeAccount.isEmpty()){
            log.info("없는 계정입니다.");
            return false;
        }
        Account existAccount = maybeAccount.get();

//        일반 회원은 사용 불가
//        if (existAccount.getRule() == normal){
//            return false
//        }

//        accountRepository.deleteByUserToken();
        return null;
    }

    // 계정 수정 기능
    @Override
    public Account modify(String email, AccountModifyRequestForm accountModifyRequestForm) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);

        if (maybeAccount.isEmpty()){
            log.info("없는 계정입니다.");
            return null;
        }
        Account account = maybeAccount.get();

        account.setEmail(accountModifyRequestForm.getEmail());
        account.setPassword(accountModifyRequestForm.getPassword());


        return accountRepository.save(account);
    }

}
