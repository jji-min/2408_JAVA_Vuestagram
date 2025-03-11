package com.example.vuestagram.security;

import com.example.vuestagram.util.jwt.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor // final로 선언된 객체(프로퍼티) 생성자 필요없게 해줌
public class TokenAuthenticationFilter extends OncePerRequestFilter { // 토큰 가져와서 세팅, 필터로 동작함, 필터단계에서 최초로 한번 실행됨
    private final JwtUtil jwtUtil;
    private final SecurityAuthenticationProvider securityAuthenticationProvider;

    @Override
    // 엑세스 토큰의 유효 여부를 확인하고 인증 정보를 스프링 시큐리티에 설정하는 메소드
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 쿠키에서 토큰 획득
        String token = jwtUtil.getAccessTokenInCookie(request);

        if (token != null) {
            try {
                // Security 인증 정보 설정
                SecurityContextHolder.getContext().setAuthentication(securityAuthenticationProvider.authenticate(token));
            } catch(Exception e) {
                throw new RuntimeException("사용할 수 없는 토큰");
            }
        }

        filterChain.doFilter(request, response); // 다음 필터 호출
    }
}
