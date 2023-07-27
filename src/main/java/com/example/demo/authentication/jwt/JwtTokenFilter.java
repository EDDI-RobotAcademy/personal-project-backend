package com.example.demo.authentication.jwt;

import com.example.demo.authentication.redis.RedisService;
import com.example.demo.domain.account.entity.Account;
import com.example.demo.domain.account.service.AccountService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        String accessToken = null;
        String refreshToken = null;

        if(cookies == null){
            filterChain.doFilter(request, response);
            return;
        }
        for(Cookie cookie : cookies) {
            if (cookie.getName().equals("AccessToken")) {
                accessToken = cookie.getValue();

                // accessToken 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
                if (accessToken == null) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            if (cookie.getName().equals("RefreshToken")) {
                refreshToken = cookie.getValue();

                // refreshToken 값이 비어있으면 => AccessToken 재발급 X
                if (refreshToken == null) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
        }

        if(!(redisService.getValueByToken(accessToken) ==null)){
            filterChain.doFilter(request, response);
            log.info("logout token");
            return;
        }

        String token = JwtTokenUtil.isExpired(accessToken, refreshToken, secretKey);
        Cookie accessCookie = jwtTokenUtil.generateCookie("AccessToken", token, 24*60*60);
        response.addCookie(accessCookie);

        // AccessToken에서 Email 추출
        String email = JwtTokenUtil.getEmail(token, refreshToken, secretKey);

        // 추출한 Email로 Account 찾아오기
        Account loginAccount = accountService.getLoginAccountByEmail(email);

        // loginAccount 정보로 UsernamePasswordAuthenticationToken 발급
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginAccount.getEmail(), null, List.of(new SimpleGrantedAuthority(loginAccount.getRole().toString())));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // 권한 부여
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
