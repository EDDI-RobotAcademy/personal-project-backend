package com.example.demo.account.entity;

import com.example.demo.board.notice.entity.NoticeBoard;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Account {
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;
    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<NoticeBoard> noticeBoards = new ArrayList<>();
    @Setter
    private String email;
    @Setter
    private String password;
    @Setter
    private String accountName;
    @Setter
    private String accountBirth;
    @Setter
    private String accountPhone;
    @Setter
    private String accountAddress;
    @Setter
    private String userToken;
    private String userType;

    public Account(String email, String password, String accountName, String accountBirth, String accountPhone, String accountAddress, String userType) {
        this.email = email;
        this.password = password;
        this.accountName = accountName;
        this.accountBirth = accountBirth;
        this.accountPhone = accountPhone;
        this.accountAddress = accountAddress;
        this.userType = userType;
    }

    public Account(String userToken) {
        this.accountId = accountId;
        this.email = email;
        this.password = password;

    }

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Account(Long accountId) {
        this.accountId = accountId;
    }

    public Account(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }
}