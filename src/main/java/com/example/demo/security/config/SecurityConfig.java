package com.example.demo.security.config;

import com.example.demo.security.jwt.CustomAuthenticationEntryPoint;
import com.example.demo.security.jwt.JwtAuthenticationFilter;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.security.jwt.service.AccountDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    private final AccountDetailsService accountDetailsService;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

//    private static String[] PERMIT_URL_ARRAY = {"/account/sing-up", "/account/log-in", "/account/check-email/**"};

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .exceptionHandling() // 예외 처리
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .requestMatchers(HttpMethod.GET,"/account/check-email/**").permitAll()
                .requestMatchers("/account/reissue").permitAll()
                .requestMatchers("/account/myPage").permitAll()
                .requestMatchers("/account/sign-up").permitAll()
                .requestMatchers("/account/log-in").permitAll()
                .requestMatchers("/board/**").permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider, accountDetailsService),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();

        }
}

//requestMatchers