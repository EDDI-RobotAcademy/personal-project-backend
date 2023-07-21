package com.example.demo.member.entity;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.comment.entity.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

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
    private String nickname;

    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private MemberRole memberRole;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Comment> Comment;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberBoard> memberBoardList;

    @Getter
    @Setter
    private String userToken;

    public Member(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    public Member(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
        this.userToken = UUID.randomUUID().toString();
    }
}
