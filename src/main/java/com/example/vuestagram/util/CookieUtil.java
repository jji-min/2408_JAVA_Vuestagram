package com.example.vuestagram.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Optional;

@Component
public class CookieUtil {
    // Request Header에서 특정 쿠키를 획득하는 메소드
    public Cookie getCookie(HttpServletRequest request, String name) {
        // 쿠키가 없는 경우도 있으므르
        // Optional.ofNullable 메소드를 사용해서, null을 가질 수 있는 Optional 생성
        return Arrays.stream(Optional.ofNullable(request.getCookies()) // 쿠기를 배열로 담아둠
                    .orElseThrow(() -> new RuntimeException("쿠키를 찾을 수 없다.")) // Optional 만들 때 예외처리
                ) // Stream<Cookie[]> 생성
                .filter(item -> item.getName().equals(name)) // 필터 조건에 맞는 Stream을 리턴(중간 연산자)
                .findFirst() // 필터 조건에 맞는 첫번째 아이템을 선택해서 Optional로 리턴(최종 연산)
                .orElseThrow(() -> new RuntimeException("쿠키를 찾을 수 없다.")); // 전체 예외처리
    }

    // Response Header에 쿠키 설정 메소드
    // 쿠키는 보여주기만 하면 되니까 void
    public void setCookie(HttpServletResponse response, String name, String value, int maxAge, String domain) {
        Cookie cookie = new Cookie(name, value); // (이름, 값), 키에 값을 가지는 쿠기 객체가 생성됨
        cookie.setPath(domain); // 특정 요청으로 들어올 때만 쿠키를 넘겨주도록 설정
        cookie.setMaxAge(maxAge); // 만료 시간 설정
        cookie.setHttpOnly(true); // 보안 쿠키로 설정, 프론트에서 js 쿠키 획득이 불가능
        response.addCookie(cookie);
    }
}
