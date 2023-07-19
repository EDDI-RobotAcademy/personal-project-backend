package com.example.demo.account.repository;

import com.example.demo.account.controller.form.MyPageRequestForm;
import com.example.demo.account.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

}
