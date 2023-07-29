package com.example.demo.member.service;

import com.example.demo.board.entity.MemberBoard;
import com.example.demo.board.reposiitory.MemberBoardRepository;
import com.example.demo.member.controller.form.MemberLoginResponseForm;
import com.example.demo.member.controller.form.MemberRequestForm;
import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.MemberRole;
import com.example.demo.member.entity.Role;
import com.example.demo.member.entity.RoleType;
import com.example.demo.member.repository.MemberRepository;
import com.example.demo.member.repository.MemberRoleRepository;
import com.example.demo.member.repository.RoleRepository;
import com.example.demo.member.service.request.MemberLoginRequest;
import com.example.demo.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    final private MemberRepository memberRepository;
    final private MemberRoleRepository memberRoleRepository;
    final private RoleRepository roleRepository;
    final private RedisService redisService;
    final private MemberBoardRepository boardRepository;

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
    public Boolean checkNickname(String nickname) {
        final Optional<Member> presentNickname = memberRepository.findByNickname(nickname);
        if(presentNickname.isPresent()){
            return false;
        }
        return true;
    }

    @Override
    public MemberLoginResponseForm login(MemberLoginRequest request) {
        final Optional<Member> maybeMember = memberRepository.findByEmail(request.getEmail());
        if(maybeMember.isEmpty()){
            return null;
        }
        final Member member = maybeMember.get();
        if(member.getPassword().equals(request.getPassword())){
            final String userToken = UUID.randomUUID().toString();
            redisService.setKeyAndValue(userToken, member.getId());
            final Role role = memberRoleRepository.findRoleInfoMember(member);
            return new MemberLoginResponseForm(userToken, role.getRoleType().name(),member.getNickname());
        }
        return null;
    }
    @Override
    public Boolean checkEmail(String email) {
        final Optional<Member> presentEmail = memberRepository.findByEmail(email);
        if(presentEmail.isPresent()){
            return false;
        }
        return true;
    }
    @Override
    public Member signUpKakao(String email, String nickname) {
        Optional<Member> isMember = memberRepository.findByEmail(email);

        if(isMember.isEmpty()){
            Member member = new Member(email, nickname);
            Member savedMember = memberRepository.save(member);
            final Role role = roleRepository.findByRoleType(RoleType.valueOf("NORMAL")).get();
            final MemberRole memberRole = new MemberRole(member, role);
            memberRoleRepository.save(memberRole);

            final Role userRole = memberRoleRepository.findRoleInfoMember(member);

            return savedMember;
        }

        return isMember.get();
    }

    @Override
    public Boolean checkMember(Long memberId, HttpHeaders headers) {
        Long maybeMemberId = redisService.getValueByKey(Objects.requireNonNull(headers.get("authorization")).get(0));
        if (maybeMemberId.equals(memberId)){
            return true;
        }
        return false;
    }



}
