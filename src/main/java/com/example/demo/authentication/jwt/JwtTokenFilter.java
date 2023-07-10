package com.example.demo.authentication.jwt;

import com.example.demo.account.entity.Account;
import com.example.demo.account.service.AccountService;
import com.example.demo.authentication.redis.RedisService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

// OncePerRequestFilter : 매번 들어갈 때 마다 체크 해주는 필터
@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final AccountService accountService;
    private final String secretKey;
    private final RedisService redisService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Cookie[] cookies =  request.getCookies();
        String token = null;

        if(cookies == null){
            filterChain.doFilter(request, response);
            return;
        }
        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                String authorizationHeader = cookie.getValue();
                log.info("authorizationHeader : " + authorizationHeader);
                // authorizationHeader 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
                if (authorizationHeader == null) {
                    filterChain.doFilter(request, response);
                    return;
                }

//             전송받은 값에서 뒷부분(Jwt Token) 추출
                token = authorizationHeader.split(" ")[0];

                if(!(redisService.getValueByToken(token) ==null)){
                    filterChain.doFilter(request, response);
                    log.info("logout token");
                    return;
                }
                // 전송받은 Jwt Token이 만료되었으면 RefreshToken 확인하여 재발급 여부 확인 => 다음 필터 진행(인증 X)
                if (JwtTokenUtil.isExpired(token, secretKey)) {
                    for(Cookie cookie1 : cookies){

                        if(cookie1.getName().equals("RefreshToken")){
                            String refreshToken = cookie1.getValue();

                            if(!JwtTokenUtil.isExpired(refreshToken, secretKey)){
                                Claims claims = JwtTokenUtil.extractClaims(refreshToken, secretKey);
                                String email = claims.getSubject();
                                System.out.println(email);

                                token = JwtTokenUtil.createAccessToken(email, secretKey, 60*60);

                                Cookie accessCookie = jwtTokenUtil.generateCookie("AccessToken", token, 60*60);

                                response.addCookie(accessCookie);
                                break;
                            }
                            else{
                                filterChain.doFilter(request, response);
                                return;
                            }
                        }
                    }
                }
                // Jwt Token에서 Email 추출
                String email = JwtTokenUtil.getEmail(token, secretKey);

                // 추출한 Email로 Account 찾아오기
                Account loginAccount = accountService.getLoginAccountByEmail(email);

                // loginAccount 정보로 UsernamePasswordAuthenticationToken 발급
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        loginAccount.getEmail(), null, List.of(new SimpleGrantedAuthority(loginAccount.getRole().toString())));
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 권한 부여
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
                break;
            }
        }
    }
}
