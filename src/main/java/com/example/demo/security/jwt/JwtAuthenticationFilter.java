package com.example.demo.security.jwt;

import com.example.demo.security.jwt.service.AccountDetailsService;
import com.example.demo.security.jwt.subject.Subject;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

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
            String accessToken = authorization.substring(7);

            try {
                Subject subject = jwtProvider.getSubject(accessToken);
                String requestURI = request.getRequestURI();

                // redis 요청을 통해 rft 를 받기위해 추가된 코드
                if (subject.getTypes().equals("refreshToken") && !requestURI.equals("/account/reissue")) {
                    throw new JwtException("토큰을 확인하세요");
                }
                // 

                UserDetails userDetails = accountDetailsService.loadUserByUsername(subject.getEmail());
                Authentication token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(token);
            } catch (JwtException e) {
                request.setAttribute("exception", e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
