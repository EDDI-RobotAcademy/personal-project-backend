package com.example.demo.member.service;

import com.example.demo.member.controller.form.MemberRequestForm;
import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.MemberRole;
import com.example.demo.member.entity.Role;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.repository.MemberRoleRepository;
import com.example.demo.member.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    final private MemberRepository memberRepository;
    final private MemberRoleRepository memberRoleRepository;
    final private RoleRepository roleRepository;

    @Override
    public Boolean register(MemberRequestForm requestForm) {

        final Optional<Member> maybeMember = memberRepository.findByEmail(requestForm.getEmail());
        if(maybeMember.isPresent()) {
            log.debug("이미 등록된 회원이라 가입할 수 없습니다.");
            return false;
        }
        final Member member = requestForm.toMember();
        memberRepository.save(member);
        final Role role = roleRepository.findByRoleType(requestForm.getRoleType()).get();
        final MemberRole memberRole = new MemberRole(member, role);
        memberRoleRepository.save(memberRole);
        return true;
    }


    @Override
    public Boolean checkNickName(String nickName) {
        final Optional<Member> presentNickName = memberRepository.findByNickName(nickName);
        if(presentNickName.isPresent()){
            return false;
        }
        return true;
    }
}
