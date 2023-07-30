package com.example.demo.account.entity;

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

    // 사업자 회원
    private String businessNumber;

    public AccountRole(Role role, Account account) {
        this.role = role;
        this.account = account;
    }

    public AccountRole(Role role, Account account, String businessNumber) {
        this.role = role;
        this.account = account;
        this.businessNumber = businessNumber;
    }
}
