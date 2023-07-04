package com.example.demo.account.service;

import com.example.demo.account.controller.form.request.*;
import com.example.demo.account.controller.form.response.AccountLoginResponseForm;
import com.example.demo.account.controller.form.response.AccountPasswordResponseForm;
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

//        Optional<Account> maybeAccount = accountRepository.findByEmail(account.getEmail());
//        if(maybeAccount.isPresent()) {
//            return null;
//        } // 아이디 중복 확인하면서 확인할 것이기 때문에 구지 안해도 됌

        Account savedAccount = accountRepository.save(account);
        System.out.println(savedAccount);
        return savedAccount;
    }

    // 로그인 기능
    @Override
    public AccountLoginResponseForm login(AccountLoginRequestForm requestForm) {
        Account account = requestForm.toAccount();
        Optional<Account> maybeAccount = accountRepository.findByEmail(account.getEmail());

        if(maybeAccount.isEmpty()){
            return new AccountLoginResponseForm(account.getUserToken(),"WRONG_ID");
        }
        Account savedAccount = maybeAccount.get();
        if (savedAccount.getPassword().equals(account.getPassword())) {
            savedAccount.setUserToken(UUID.randomUUID().toString());
            accountRepository.save(savedAccount);
            return new AccountLoginResponseForm(savedAccount.getUserToken(),"SUCCESS_LOGIN");
        }
        return new AccountLoginResponseForm(account.getUserToken(),"WRONG_PW");
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
        return accountRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
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
    public Account modify(AccountModifyRequestForm requestForm) {
        Optional<Account> maybeAccount = accountRepository.findByUserToken(requestForm.getUserToken());

        if (maybeAccount.isEmpty()){
            log.info("없는 계정입니다.");
            return null;
        }
        Account account = maybeAccount.get();

        account.setPassword(requestForm.getPassword());
        account.setAccountName(requestForm.getAccountName());
        account.setAccountBirth(requestForm.getAccountBirth());
        account.setAccountPhone(requestForm.getAccountPhone());
        account.setAccountAddress(requestForm.getAccountAddress());

        return accountRepository.save(account);
    }

    // 비밀번호 찾기 기능
    @Override
    public AccountPasswordResponseForm passwordFind(AccountPasswordFindRequestForm requestForm) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(requestForm.getEmail());
        if(maybeAccount.isEmpty()) {
            log.info("없는 계정 입니다.");
            return new AccountPasswordResponseForm("","WRONG_ID");
        }
        if(!maybeAccount.get().getAccountName().equals(requestForm.getAccountName())){
            log.info("이름이 일치하지 않습니다.");
            return new AccountPasswordResponseForm("","WRONG_NAME");
        }
        if(!maybeAccount.get().getAccountBirth().equals(requestForm.getAccountBirth())){
            log.info("생년월일을 잘못 입력 하셨습니다.");
            return new AccountPasswordResponseForm("","WRONG_BIRTH");
        }

        return new AccountPasswordResponseForm(maybeAccount.get().getPassword(),"SUCCESS_FIND_PASSWORD");
    }

    @Override
    public Boolean goMypage(AccountGoMypageForm accountGoMypageForm) {

        Optional<Account> maybeAccount = accountRepository.findByUserToken(accountGoMypageForm.getUserToken());

        if(maybeAccount.isEmpty()){
            log.info("에러 발생");
        }

        if(maybeAccount.get().getPassword().equals(accountGoMypageForm.getPassword())){
            return true;
        }else {
            log.info("틀린 비밀번호 입니다.");
            return false;
        }

    }

}
