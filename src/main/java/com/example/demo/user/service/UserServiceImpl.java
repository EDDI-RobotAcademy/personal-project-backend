package com.example.demo.user.service;

import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final private UserRepository userRepository;

    @Override
    public Boolean checkEmailDuplication(String email) {
        Optional<User> maybeUser = userRepository.findByEmail(email);

        if (maybeUser.isPresent()){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public Boolean signUp(UserRegisterRequest request) {
        userRepository.save(request.toUser());

        return true;
    }
}
