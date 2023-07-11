package com.example.demo.config;

import com.example.demo.account.entity.RoleType;
import com.example.demo.account.service.AccountService;
import com.example.demo.authentication.jwt.JwtTokenFilter;
import com.example.demo.authentication.jwt.JwtTokenUtil;
import com.example.demo.authentication.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AccountService accountService;
    private final RedisService redisService;
    private final JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.secret}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(new JwtTokenFilter(accountService, secretKey, redisService, jwtTokenUtil), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .requestMatchers("/account/register", "/account/login", "/account/email-check", "/account/nickname-check").permitAll()
                .anyRequest().hasAuthority(RoleType.NORMAL.name())
                .and().build();
    }
}
