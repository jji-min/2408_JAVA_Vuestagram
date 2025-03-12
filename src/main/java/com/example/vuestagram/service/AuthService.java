package com.example.vuestagram.service;

import com.example.vuestagram.dto.request.LoginRequestDTO;
import com.example.vuestagram.model.User;
import com.example.vuestagram.repository.UserRepository;
import com.example.vuestagram.util.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // 빈등록도 자동으로 해줌
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String login(LoginRequestDTO loginRequestDTO) {
        Optional<User> result = userRepository.findByAccount(loginRequestDTO.getAccount());

        // 유저 존재 여부 체크
        if(result.isEmpty()) {
            throw new RuntimeException("존재하지 않는 유저입니다.");
        }

        // 비밀번호 체크
        if(!(passwordEncoder.matches(loginRequestDTO.getPassword(), result.get().getPassword()))) {
            // (암호화가 되어있지 않은 패스워드, 암호화가 되어있는 패스워드-검색해서 가져온 패스워드)
            // 같지 않을 때
            throw new RuntimeException("비밀번호가 틀렸습니다.");
        }

        // 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(result.get());
        String refreshToken = jwtUtil.generateRefreshToken(result.get());

        return accessToken + " || " + refreshToken;
    }
}
