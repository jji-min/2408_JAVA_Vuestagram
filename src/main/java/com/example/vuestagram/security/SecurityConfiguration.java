package com.example.vuestagram.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // 설정과 관련된 것이라는 걸 알려주는 것
@EnableWebSecurity // 스프링 시큐리티 설정을 활성화, 5.7버전 이상에서는 생략 가능
public class SecurityConfiguration {
    @Bean // 우리가 따로 인스턴스화하는 것이 아니라 스프링 부트가 알아서 인스턴스화하고 관리하기 위해 Bean 등록, 자바가 알아서 객체관리하는 기능
    // 비밀번호 암호화 관련 구현체 정의 및 빈등록
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); } // 비밀번호를 암호화하는 것

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 비활성화 -> JWT를 이용한 토큰 인증 방식을 만들고 있기 때문
                .httpBasic(h -> h.disable()) // SSR이 아니므로 화면 생성 비활성 설정
                .formLogin(form -> form.disable()) // SSR이 아니므로 폼로그인 기능 비활성 설정
                .csrf(csrf -> csrf.disable()) // SSR이 아니므로 CSRF 토큰 인증 비활성 설정

                .authorizeHttpRequests(request -> // 리퀘스트에 대한 인가 체크 처리
                    request.requestMatchers("/api/login").permitAll() // '/api/login'은 인가 없이 접근 가능, 로그인 안해도 허용, 모두 허용
                            .anyRequest().authenticated() // 위에서 정의한 것들 이외에는 인가 필요
                )
                .build();
    }
}
