package com.example.demo.security.jwt;

import com.example.demo.security.jwt.service.AccountResponse;
import com.example.demo.redis.RedisService;
import com.example.demo.security.jwt.subject.Subject;
import com.example.demo.security.jwt.subject.TokenResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {

    final private RedisService redisService;
    final private ObjectMapper objectMapper;


    private SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    public SecretKey getKey() {
        log.info("Provider 키값: " + key);
        return key;
    }

    @Value("${spring.jwt.live.atk}")
    private Long accessTokenLive;

    @Value("${spring.jwt.live.rtk}")
    private Long refreshTokenLive;

    // 로그인 토큰 생성
    public TokenResponse createTokenByLogin(AccountResponse accountResponse) {
        try {
            // access 토큰 생성
            Subject tokenSubject = Subject.accessToken(accountResponse.getAccountId(), accountResponse.getEmail());
            // refresh 토큰 생성
            Subject refreshTokenSubject = Subject.refreshToken(accountResponse.getAccountId(), accountResponse.getEmail());
            // 유효 시간 설정
            String accessToken = createToken(tokenSubject, accessTokenLive);
            String refreshToken = createToken(refreshTokenSubject, refreshTokenLive);

            return new TokenResponse(accessToken, refreshToken); // act, rft 토큰 값 전송
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // 예외 처리
            return null; // 예외 발생 시 null 반환 또는 적절한 오류 처리
        }
    }

    // 토큰 생성
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

    // 토큰에서 인증 정보 조회
    public Subject getSubject (String accessToken) throws JsonProcessingException {
        // postman 오류 ->message": "Signed Claims JWSs are not supported.", "status": "UNAUTHORIZED"
        // parseClaimsJwt -> parseClaimsJws 수정
        String subjectStr = Jwts.parser().setSigningKey(key).parseClaimsJws(accessToken).getBody().getSubject();
        log.info("subjectStr : "+subjectStr);
        // 변경 후 -> "status": 403, "error": "Forbidden" 에러

//        Jws<Header, Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token);

        return objectMapper.readValue(subjectStr, Subject.class);
    }

    public TokenResponse reissueAccessToken(AccountResponse accountResponse) throws JsonProcessingException {
        String refreshTokenRedis = redisService.getValues(accountResponse.getEmail());

        if (Objects.isNull(refreshTokenRedis)) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "인증 정보가 만료되었습니다.");
        Subject accessTokenSubject = Subject.accessToken(accountResponse.getAccountId(), accountResponse.getEmail());
        String accessToken = createToken(accessTokenSubject, accessTokenLive);

        return new TokenResponse(accessToken, null);
    }
}
