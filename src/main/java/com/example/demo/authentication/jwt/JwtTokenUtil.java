package com.example.demo.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {
    // JWT Token 발급
    public static TokenInfo createToken(String email, String key, long expireTimeMs) {
        // Claim = Jwt Token에 들어갈 정보
        // Claim에 loginId를 넣어 줌으로써 나중에 loginId를 꺼낼 수 있음
        Claims claims = Jwts.claims();
        claims.put("email", email);

        String accessToken = Jwts.builder()       // 토큰 생성
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))      //  시작 시간 : 현재 시간기준으로 만들어짐
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))     // 끝나는 시간 : 지금 시간 + 유지할 시간(입력받아옴)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return TokenInfo.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static String createAccessToken(String email, String key, long expireTimeMs) {
        // Claim = Jwt Token에 들어갈 정보
        // Claim에 email을 넣어 줌으로써 나중에 email을 꺼낼 수 있음
        Claims claims = Jwts.claims();
        claims.put("email", email);

        return Jwts.builder()       // 토큰 생성
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))      //  시작 시간 : 현재 시간기준으로 만들어짐
                .setExpiration(new Date(System.currentTimeMillis() + expireTimeMs))     // 끝나는 시간 : 지금 시간 + 유지할 시간(입력받아옴)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

    // Claims에서 email 꺼내기
    public static String getEmail(String token, String secretKey) {
        return extractClaims(token, secretKey).get("email").toString();
    }

    public static Date getExp(String token, String secretKey){
        return extractClaims(token, secretKey).getExpiration();
    }

    // 발급된 Token이 만료 시간이 지났는지 체크
    public static boolean isExpired(String token, String secretKey) {
        Date expiredDate = extractClaims(token, secretKey).getExpiration();
        // Token의 만료 날짜가 지금보다 이전인지 check
        return expiredDate.before(new Date());
    }

    // SecretKey를 사용해 Token Parsing
    public static Claims extractClaims(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public Cookie generateCookie(String name, String value, int time){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(time);
        cookie.setHttpOnly(true);

        return cookie;
    }
}