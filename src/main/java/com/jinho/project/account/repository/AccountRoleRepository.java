package com.jinho.project.account.repository;

import com.jinho.project.account.entity.Account;
import com.jinho.project.account.entity.AccountRole;
import com.jinho.project.account.entity.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {
    @Query("select ar.role from AccountRole ar join fetch Role r where ar.account = :account")
    Role findRoleByAccount(Account account);
    @Transactional
    void deleteAllByAccountId(Long id);


//    @Query("select ar.role from AccountRole ar join fetch Role r where ar.account = :account")
//    Role findRoleByAccount(Account account);
//    @Query("SELECT ar FROM AccountRole ar JOIN FETCH ar.role WHERE ar.account.id = :accountId")
//    Optional<AccountRole> findByAccount_IdWithRole(@Param("accountId") Long accountId);

}

