package com.example.demo.account.service;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import com.example.demo.account.controller.request.AccessRegisterRequest;
import com.example.demo.account.controller.request.AccountRegisterRequest;
import com.example.demo.account.controller.request.MyPageRequestForm;
import com.example.demo.account.entity.Account;
import com.example.demo.security.jwt.service.AccountResponse;
import com.example.demo.account.entity.AccountRole;
import com.example.demo.account.entity.Role;
import com.example.demo.account.repository.AccountRepository;
import com.example.demo.account.repository.AccountRoleRepository;
import com.example.demo.account.repository.RoleRepository;
import com.example.demo.redis.RedisService;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.security.jwt.subject.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{

    final private AccountRepository accountRepository;
    final private AccountRoleRepository accountRoleRepository;
    final private RoleRepository roleRepository;
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
    public TokenResponse login(AccountLoginRequestForm form) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(form.getEmail());

        if (maybeAccount.isPresent()) {
            if (passwordEncoder.matches(form.getPassword(), maybeAccount.get().getPassword())) {
                Account account = maybeAccount.get();
                TokenResponse tokenResponse = jwtProvider.createTokenByLogin(AccountResponse.of(account));

                String accessToken = tokenResponse.getAccessToken();
                String refreshToken = tokenResponse.getRefreshToken();
                form.setEmail(accessToken, refreshToken);

                log.info("accessToken: " + accessToken);
                log.info("refreshToken: " + refreshToken);
                redisService.setKeyAndValue(refreshToken, account.getEmail());
                return tokenResponse;
            }
        }
        return null;
    }

    @Override
    public Boolean findAccountInfo(MyPageRequestForm form, String accessToken) {
        Optional<Account> maybeAccount = accountRepository.findByEmail(form.getEmail());

        if (maybeAccount.isPresent()) {
            Account account = maybeAccount.get();

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey("${spring.jwt.key}")
                        .parseClaimsJws(accessToken)
                        .getBody();
                String email = claims.getSubject();
                log.info("email: " + email);
                log.info("accessToken: " + accessToken);

                if (email.equals(account.getEmail())) {
                    // accessToken이 유효하고 계정의 이메일과 일치하는 경우 사용자 정보를 반환합니다.
                    account.getEmail();
                    account.getName();
                    account.getPhoneNumber();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
