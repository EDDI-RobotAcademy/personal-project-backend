package com.jinho.project.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class AccountRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_Id")
    private Role role;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_Id")
    private Account account;

//    private Long businessNumber;    // 사업자 회원의 경우 사업자번호 포함(10자리 숫자)

    public AccountRole(Role role, Account account) {
        this.role = role;
        this.account = account;
    }
}
