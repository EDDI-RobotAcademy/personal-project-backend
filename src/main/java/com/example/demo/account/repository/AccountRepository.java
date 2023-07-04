package com.example.demo.account.repository;

import com.example.demo.account.controller.form.request.AccountUserTokenRequestForm;
import com.example.demo.account.entity.Account;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    Optional<Account> findByUserToken(String userToken);

    @Modifying
    @Transactional
    void deleteByUserToken(String userToken);
}
