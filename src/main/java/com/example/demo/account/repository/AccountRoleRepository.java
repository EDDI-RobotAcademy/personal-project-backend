package com.example.demo.account.repository;

import com.example.demo.account.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {
    Optional<AccountRole> findByBusinessNumber(Long businessNumber);
}
