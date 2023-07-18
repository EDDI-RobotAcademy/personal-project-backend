package com.example.demo.account.repository;

import com.example.demo.account.controller.form.MyPageRequestForm;
import com.example.demo.account.entity.Account;
//import com.example.demo.account.entity.AccountProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.*;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);
}
