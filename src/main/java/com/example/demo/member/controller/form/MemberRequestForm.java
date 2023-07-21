package com.example.demo.member.controller.form;


import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.MemberRole;
import com.example.demo.member.entity.Role;
import com.example.demo.member.entity.RoleType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class MemberRequestForm {

    final private String email;
    final private String password;
    final private String nickname;
    final private RoleType roleType;

    public Member toMember(String userToken) {
        return new Member(email, password, nickname, userToken);
    }

    public MemberRole toMemberRole(Member member){
        return new MemberRole(member, new Role(roleType));
    }
}
