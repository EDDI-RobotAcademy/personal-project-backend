package com.example.demo.security.jwt;

import com.example.demo.account.entity.Account;
import com.example.demo.security.jwt.subject.Subject;
import com.example.demo.security.jwt.subject.TokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    final private ObjectMapper objectMapper;

    @Value("${spring.jwt.key}")
    private String key;

    @Value("${spring.jwt.live.atk}")
    private Long accessTokenLive;

    @PostConstruct
    protected void init () {
        key = Base64.getEncoder().encodeToString(key.getBytes());
    }


    public TokenResponse createTokenByLogin(Account account) {
        try {
            Subject tokenSubject = Subject.accessToken(account.getId(), account.getEmail());
            String accessToken = createToken(tokenSubject, accessTokenLive);
            return new TokenResponse(accessToken, null); // accessToken 값을 첫 번째 매개변수로 설정
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // 예외 처리
            return null; // 예외 발생 시 null 반환 또는 적절한 오류 처리
        }
    }

    private String createToken(Subject subject, Long tokenLive) throws JsonProcessingException {
        try {
            String subjectStr = objectMapper.writeValueAsString(subject);
            Claims claims = Jwts.claims().setSubject(subjectStr);
            Date date = new Date();
            return Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(date)
                    .setExpiration(new Date(date.getTime() + tokenLive))
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // 예외 처리
            throw e; // 예외 다시 던지기
        }
    }
}
