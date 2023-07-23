package com.example.demo.account.service;

import com.example.demo.account.controller.response.LoginResponse;
import com.example.demo.account.controller.response.MyPageResponse;
import com.example.demo.account.repository.ProfileRepository;
import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.request.AccessRegisterRequest;
import com.example.demo.account.controller.request.AccountRegisterRequest;
import com.example.demo.account.entity.Account;
import com.example.demo.account.entity.Profile;
import com.example.demo.security.jwt.service.AccountResponse;
import com.example.demo.account.entity.AccountRole;
import com.example.demo.account.entity.Role;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.repository.AccountRoleRepository;
import com.example.demo.account.repository.RoleRepository;
import com.example.demo.redis.RedisService;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.security.jwt.subject.TokenResponse;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    final private AccountRepository accountRepository;
    final private AccountRoleRepository accountRoleRepository;
    final private RoleRepository roleRepository;
    final private ProfileRepository profileRepository;
    final private PasswordEncoder passwordEncoder;
    final private JwtProvider jwtProvider;
    final private RedisService redisService;

    // 회원 가입
    @Override
    public Boolean signUp(AccountRegisterRequest request) {
        final Optional<Account> maybeAccount = accountRepository.findByEmail(request.getEmail());

        if (maybeAccount.isPresent()) {
            return false;
        }

        Account account = request.toAccount();
        String password = passwordEncoder.encode(request.getPassword());
        account.setPassword(password);
        accountRepository.save(account);

        final Role role = roleRepository.findByRoleType(request.getRoleType()).get();
        final AccountRole accountRole = new AccountRole(account, role);
        accountRoleRepository.save(accountRole);

        return true;
    }

    @Override
    public Boolean accessSignUp(AccessRegisterRequest request) {
        final Optional<Account> maybeAccount = accountRepository.findByEmail(request.getEmail());

        if (maybeAccount.isPresent() || !request.getAccessNumber().equals("123-456")) {
            return false;
        }

        Account account = request.toAccount();
        String password = passwordEncoder.encode(request.getPassword());
        account.setPassword(password);
        accountRepository.save(account);

        final Role role = roleRepository.findByRoleType(request.getRoleType()).get();
        final AccountRole accountRole = new AccountRole(account, role);

        accountRoleRepository.save(accountRole);

        return true;
    }

    // 이메일 중복 확인
    @Override
    public Boolean checkEmail(String email) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(email);

        if (maybeAccount.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    // 로그인
    @Override
    public LoginResponse login(AccountLoginRequestForm form, Long accountId) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(form.getEmail());

        if (maybeAccount.isPresent()) {
            if (passwordEncoder.matches(form.getPassword(), maybeAccount.get().getPassword())) {
                Account account = maybeAccount.get();
                TokenResponse tokenResponse = jwtProvider.createTokenByLogin(AccountResponse.of(account));

                String accessToken = tokenResponse.getAccessToken();
                String refreshToken = tokenResponse.getRefreshToken();
                accountId = account.getId();
                form.setEmail(accessToken, refreshToken);
                redisService.setKeyAndValue(refreshToken, account.getEmail());
                LoginResponse loginResponse = new LoginResponse(accountId, accessToken, refreshToken);
                return loginResponse;
            }
        }
        return null;
    }

    @Override
    public MyPageResponse findAccountInfo(String accessToken) {
        SecretKey key = jwtProvider.getKey();
        Jws<Claims> claims = Jwts.parser().setSigningKey(key)
                .parseClaimsJws(accessToken.replace(" ", "").replace("Bearer", ""));

        String email = claims.getBody().getSubject();
        email = email.substring(email.indexOf("\"email\":\"") + 9, email.indexOf("\",\"types\""));

        Optional<Account> maybeAccount = accountRepository.findByEmail(email);
        if (maybeAccount.isPresent()) {
            Account account = maybeAccount.get();
            log.info("account: " + account);
            Optional<Profile> maybeProfile = profileRepository.findByAccount(account);
            if (maybeProfile.isPresent()) {
                Profile profile = maybeProfile.get();
                MyPageResponse response = new MyPageResponse(profile.getEmail(), profile.getName(), profile.getPhoneNumber());
                return response;
            }
            // 기존에 연결된 profile 이 없다면 새로운 profile 을 생성하여 반환
            MyPageResponse response = new MyPageResponse(account.getEmail(), account.getName(), account.getPhoneNumber());
            Profile profile = new Profile(response.getEmail(), response.getName(), response.getPhoneNumber());
            profile.setAccount(account);
            profileRepository.save(profile);

            return response;
        }
        return null;
    }
}
