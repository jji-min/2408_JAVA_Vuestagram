package com.example.vuestagram.util.jwt.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor // 생성자 만들어줌
@ConfigurationProperties(prefix = "config.jwt") // application.yml과 연결, 실제 값이 설정 파일에 있다는 의미
public class JwtConfig {
    private final String issuer; // 토큰을 발급하는 사람이 누군지 값을 가지는 상수(ex. 회사 대표 이메일)
    private final String type; // 토큰 타입 -> jwt
    private final int accessTokenExpiry; // accesstoken 유효시간 미리세컨즈 단위
    private final int refreshTokenExpiry; // refreshtoken 유효시간
    private final String refreshTokenCookieName;
    private final int refreshTokenCookieExpiry;
    private final String secret; // 암호화할 때 사용하는 시크릿번호
    private final String headerKey; // authorization 키 가져오는 거
    private final String scheme; // Bearer를 담고 있는 프로퍼티
    private final String reissUri; // 도메인 설정, 특정 도메인이 왔을때만
}
