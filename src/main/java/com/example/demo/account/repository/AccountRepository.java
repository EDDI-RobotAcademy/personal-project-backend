package com.example.demo.account.repository;

import com.example.demo.account.entity.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByEmail(String email);

    Optional<Account> findByNickname(String nickname);

    @Modifying
    @Transactional
    @Query(value = "delete from account where email = :email", nativeQuery = true)
    void deleteByEmail(String email);
}
