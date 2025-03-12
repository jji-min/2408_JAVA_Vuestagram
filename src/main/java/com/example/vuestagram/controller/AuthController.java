package com.example.vuestagram.controller;

import com.example.vuestagram.dto.request.LoginRequestDTO;
import com.example.vuestagram.service.AuthService;
import com.example.vuestagram.util.jwt.config.JwtConfig;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
;

@RestController // REST API 컨트롤러
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public String login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO
    ) {
         return authService.login(loginRequestDTO);
    }

    @GetMapping("/test")
    public String test() {return "test";}
}
