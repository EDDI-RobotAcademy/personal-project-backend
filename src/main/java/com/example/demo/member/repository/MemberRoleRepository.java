package com.example.demo.member.repository;


import com.example.demo.member.entity.Member;
import com.example.demo.member.entity.MemberRole;
import com.example.demo.member.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
    @Query("select ar.role from MemberRole ar join fetch Role r where ar.member = :member")
    Role findRoleInfoMember(Member member);
}
