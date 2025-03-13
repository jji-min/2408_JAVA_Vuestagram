package com.example.vuestagram.controller;

import com.example.vuestagram.dto.request.LoginRequestDTO;
import com.example.vuestagram.dto.response.ResponseBase;
import com.example.vuestagram.dto.response.ResponseLogin;
import com.example.vuestagram.service.AuthService;
import com.example.vuestagram.util.jwt.config.JwtConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
;

@RestController // REST API 컨트롤러
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RestClient.Builder builder;

    @PostMapping("/login")
    public ResponseEntity<ResponseBase<ResponseLogin>> login(
            HttpServletResponse response,
            @Valid @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        ResponseLogin responseLogin = authService.login(loginRequestDTO, response);
        ResponseBase<ResponseLogin> responseBase = ResponseBase.<ResponseLogin>builder()
                                                    .status(200)
                                                    .message("로그인 성공")
                                                    .data(responseLogin)
                                                    .build();

        return ResponseEntity.status(200).body(responseBase);
    }

    @GetMapping("/test")
    public String test() {return "test";}
}
