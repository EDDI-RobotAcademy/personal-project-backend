package com.example.demo.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class Member {
    @Id
    @Getter
    @Column(name = "memberId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    private String email;
    @Getter
    private String password;
    @Getter
    private String nickName;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private MemberRole memberRole;

    @Getter
    @Setter
    private String userToken;

    public Member(String email, String password, String nickName) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
    }


}
