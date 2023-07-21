package com.example.demo.account.repository;

import com.example.demo.account.entity.Account;
import com.example.demo.account.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    @Query("SELECT p FROM Profile p JOIN FETCH p.account WHERE p.account = :account")
    Optional<Profile> findByAccount(Account account);
}
