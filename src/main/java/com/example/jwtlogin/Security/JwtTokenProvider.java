package com.example.jwtlogin.Security;

import com.example.jwtlogin.service.TokenService;
import com.example.jwtlogin.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Autowired
    public JwtTokenProvider(JwtUtil jwtUtil, TokenService tokenService) {
        this.jwtUtil = jwtUtil;
        this.tokenService = tokenService;
    }

    //JWT 생성 이후 디비에 저장하는 메서드
    public void generateToken(String username) {
        String accessToken = jwtUtil.generateAccessToken(username);
        String refreshToken = jwtUtil.generateRefreshToken(username);

        tokenService.saveToken(username, accessToken, refreshToken);
    }
}
