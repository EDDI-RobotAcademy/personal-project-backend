package com.example.demo.member.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class MemberRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY )
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Role role;

    public MemberRole(Member member, Role role) {
        this.role = role;
        this.member = member;
    }
}
