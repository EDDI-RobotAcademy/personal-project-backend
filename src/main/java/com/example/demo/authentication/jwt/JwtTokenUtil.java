package com.example.demo.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@PropertySource("classpath:jwt.properties")
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    // JWT Token 발급
    public static TokenInfo createToken(String email, String key, long expireTimeMs) {
        // Claim = Jwt Token에 들어갈 정보
        // Claim에 loginId를 넣어 줌으로써 나중에 loginId를 꺼낼 수 있음
        Claims claims = Jwts.claims();
        claims.put("email", email);

        String accessToken = Jwts.builder()       // 토큰 생성
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))      //  시작 시간 : 현재 시간기준으로 만들어짐
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))     // 끝나는 시간 : 지금 시간 + 유지할 시간(입력받아옴)
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        // Refresh Token 생성
        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setSubject("refreshToken")
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
    public static String isExpired(String accessToken, String refreshToken, String secretKey) {
        accessToken = checkClaims(accessToken, refreshToken, secretKey);
        // Token의 만료 날짜가 지금보다 이전인지 check
        return accessToken;
    }

    // SecretKey를 사용해 Token Parsing
    public static Claims extractClaims(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public static String checkClaims(String accessToken, String refreshToken, String secretKey) {
        try{
            Claims claims =Jwts.parser().setSigningKey(secretKey).parseClaimsJws(accessToken).getBody();
            log.info("try accessToken : " + accessToken);
        }catch (ExpiredJwtException e){
            Claims claims =Jwts.parser().setSigningKey(secretKey).parseClaimsJws(refreshToken).getBody();
            String email = claims.get("email").toString();
            accessToken = createAccessToken(email, secretKey, 86400000);
            log.info("catch accessToken : " + accessToken);
        }

        return accessToken;
    }

    public Cookie generateCookie(String name, String value, int time){
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(time);
        cookie.setHttpOnly(true);

        return cookie;
    }

    public String getEmailFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String email = null;

        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                String token = cookie.getValue();
                email = JwtTokenUtil.getEmail(token, secretKey);
                break;
            }
        }
        return email;
    }
}