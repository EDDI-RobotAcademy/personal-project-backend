package com.example.demo.account.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    private String name;

    private String phoneNumber;

    private String accessNumber = "123-456";

    @Setter
    private String userToken;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @OneToOne(mappedBy = "account", fetch = FetchType.LAZY)
    private AccountRole accountRole;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account(String email, String password, String name, String phoneNumber, RoleType roleType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.roleType = roleType;
    }

    public Account(String email, String password, String accessNumber, RoleType roleType) {
        this.email = email;
        this.password = password;
        this.accessNumber = accessNumber;
        this.roleType = roleType;
    }

}
