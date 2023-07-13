package com.example.demo.user.repository;

import com.example.demo.user.entity.Role;
import com.example.demo.user.entity.User;
import com.example.demo.user.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Query("select ar.role from UserRole ar join fetch Role r where ar.user = :user")
    Role findRoleInFoByUser(User user);
}
