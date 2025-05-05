package com.example.jwtlogin.controller;

import com.example.jwtlogin.dto.UserCredentials;
import com.example.jwtlogin.model.User;
import com.example.jwtlogin.service.TokenService;
import com.example.jwtlogin.repository.UserRepository;
import com.example.jwtlogin.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("등록 완료");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserCredentials credentials) {
        User user = userRepository.findByUsername(credentials.getUsername()).orElse(null);

        if (user == null || !passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        // 토큰 생성
        String accessToken = jwtUtil.generateAccessToken(user.getUsername());
        String refreshToken = jwtUtil.generateRefreshToken(user.getUsername());

        // DB에 저장
        tokenService.saveToken(user.getUsername(), accessToken, refreshToken);

        // 응답에 토큰 포함
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", accessToken);
        tokens.put("refreshToken", refreshToken);

        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // 클라이언트에서 토큰을 삭제하거나, 서버에서는 refreshToken만 삭제 가능
        return ResponseEntity.ok("로그아웃 성공");
    }
}
