package com.example.jwtlogin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private JwtUtil jwtUtil = new JwtUtil();

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentials credentials) {
        // 사용자 인증 로직 (여기서는 예시로 username만 확인)
        if (credentials.getUsername().equals("user") && credentials.getPassword().equals("password")) {
            String token = jwtUtil.generateToken(credentials.getUsername());
            return ResponseEntity.ok(token); // 로그인 성공 시 토큰 반환
        } else {
            return ResponseEntity.status(401).body("Unauthorized");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // 로그아웃은 보통 클라이언트에서 토큰을 삭제하는 방식으로 처리
        return ResponseEntity.ok("로그아웃 성공");
    }
}
