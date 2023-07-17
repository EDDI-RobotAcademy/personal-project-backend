package com.example.demo.security.jwt;

import com.example.demo.security.jwt.service.AccountDetailsService;
import com.example.demo.security.jwt.subject.Subject;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Slf4j
// 토큰을 검증하는건 여기서 코드 진행
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private final AccountDetailsService accountDetailsService;

    public JwtAuthenticationFilter(JwtProvider jwtProvider, AccountDetailsService accountDetailsService) {
        this.jwtProvider = jwtProvider;
        this.accountDetailsService = accountDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");
        if (!Objects.isNull(authorization)) {
            String accountToken = authorization.substring(7);
            try {
                Subject subject = jwtProvider.getSubject(accountToken);
                log.info("subject : " + subject.getTypes() );
                String requestURI = request.getRequestURI();

                // 토큰 타입이 refreshToken 인 경우 RequestURL 이 /account/reissue 인 경우에만 허용
                if (subject.getTypes().equals("RTK") && !requestURI.equals("/account/reissue")) {
                    throw new JwtException("토큰을 확인하세요.");
                }
                UserDetails userDetails = accountDetailsService.loadUserByUsername(subject.getEmail());
                log.info("userDetail : " + userDetails.getUsername() + userDetails.getAuthorities().toString() );
                Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
            } catch (JwtException e) {
                request.setAttribute("exception", e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
