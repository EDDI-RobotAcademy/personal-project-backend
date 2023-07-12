package com.example.demo.security.config;

import com.example.demo.account.controller.form.AccountLoginRequestForm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

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
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .formLogin().disable();

        return httpSecurity
                .authorizeHttpRequests(
                        authorize -> authorize
//                                .requestMatchers(PERMIT_URL_ARRAY).permitAll()
                                .requestMatchers(HttpMethod.GET,"/account/check-email/**").permitAll()
                                .requestMatchers("/account/sign-up").permitAll()
                                .requestMatchers("/account/log-in").permitAll()
//                                .requestMatchers("/login").permitAll()
                                .anyRequest().authenticated()

                                .and()
                )
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
