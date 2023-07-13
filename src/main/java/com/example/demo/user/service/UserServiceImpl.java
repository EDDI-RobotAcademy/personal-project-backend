package com.example.demo.user.service;

import com.example.demo.user.controller.form.UserSignInResponseForm;
import com.example.demo.user.entity.Role;
import com.example.demo.user.entity.RoleType;
import com.example.demo.user.entity.User;
import com.example.demo.user.entity.UserRole;
import com.example.demo.user.repository.*;
import com.example.demo.user.service.request.UserSignInRequest;
import com.example.demo.user.service.request.UserSignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final private UserRepository userRepository;
    final private RoleRepository roleRepository;
    final private UserRoleRepository userRoleRepository;
    final private UserTokenRepository userTokenRepository = UserTokenRepositoryImpl.getInstance();

    @Override
    public Boolean signUp(UserSignUpRequest userSignUpRequest) {
        final Optional<User> maybeUser = userRepository.findByEmail(userSignUpRequest.getEmail());
        if (maybeUser.isPresent()) {
            return false;
        }
        final User user = userRepository.save(userSignUpRequest.toUser());

        return true;
    }

    @Override
    @Transactional
    public UserSignInResponseForm signIn(UserSignInRequest request) {
        final Optional<User> maybeUser = userRepository.findByEmail(request.getEmail());
        if (maybeUser.isEmpty()) {
            return null;
        }
        final User user = maybeUser.get();
        if (user.getPassword().equals(request.getPassword())){
            final String userToken = UUID.randomUUID().toString();
            userTokenRepository.save(userToken, user.getUserId());

            final Role role = userRoleRepository.findRoleInFoByUser(user);

            return new UserSignInResponseForm(userToken, role.getRoleType().name());
        }
        return null;
    }

    @Override
    public RoleType lookup(String userToken) {
        final Long userId = userTokenRepository.findUserIdByToken(userToken);
        final Optional<User> maybeUser = userRepository.findById(userId);

        if (maybeUser.isEmpty()) {
            return null;
        }
        final User user = maybeUser.get();
        final Role role = userRoleRepository.findRoleInFoByUser(user);

        return role.getRoleType();
    }

    @Override
    public Long findUserId(String userToken) {
        return userTokenRepository.findUserIdByToken(userToken);
    }
}
