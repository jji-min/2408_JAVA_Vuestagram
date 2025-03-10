package com.example.vuestagram.util.jwt;

import com.example.vuestagram.model.User;
import com.example.vuestagram.util.jwt.config.JwtConfig;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component // 클래스 레벨 빈등록(스프링이 자동으로 관리해주도록 빈등록)
public class JwtUtil {
    private final JwtConfig jwtConfig;
    private final SecretKey secretKey; // decode 해줘야함

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;

        // Base64 인코딩 된 Secret을 디코딩 하여 Key 객체로 변환
        // HMAC 서명에 필요한 적절한 SecretKey를 제공하기 위한 것
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret()));
    }

    // 엑세스 토큰 생성 메소드
    public String generateAccessToken(User user) {
        return this.generateToken(user.getUserId(), jwtConfig.getAccessTokenExpiry()); // (PK, 유효시간)
    }

    // 리프레시 토큰 생성 메소드
    public String generateRefreshToken(User user) {
        return this.generateToken(user.getUserId(), jwtConfig.getRefreshTokenExpiry());
    }

    // 토큰 생성 메소드
    public String generateToken(long userId, int expiry) { // time to limit - ttl
        Date now = new Date(); // 현재 시간

        return Jwts.builder() // jjwt에서 자동으로 인스턴스됨. JWT 빌더 객체 생성
                .header().type(jwtConfig.getType())// header.type 셋팅
                .and() // 다른 파츠 추가 연결 메소드
                .setSubject(String.valueOf(userId)) // (user pk), unique 값 넣어줌. 보퉁 pk나 id값. valueof -> 넘겨준 값을 string으로 변환해서 가져옴
                .setIssuer(jwtConfig.getIssuer()) // (토큰 발급사)
                .setIssuedAt(now) // (토큰 발급시간). Date 객체 넘겨줌
                .setExpiration(new Date(now.getTime() + expiry)) // (토큰 만료시간). 유닉스 타임으로 바껴서 숫자로 계산
                .signWith(secretKey) // secretKey 설정
                .compact(); // 토큰 생성
    }
}
