package com.example.jwtlogin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.jwtlogin.SecurityConfig;

@RestController
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;  // UserRepository를 주입받습니다.


    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user); // 이게 있어야 DB에 저장돼!
        return ResponseEntity.ok("등록 완료");
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserCredentials credentials) {
        // 데이터베이스에서 사용자 조회
        User user = userRepository.findByUsername(credentials.getUsername()).orElse(null);

        // 사용자 정보가 없거나 비밀번호가 일치하지 않는 경우
        if (user == null || !passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        // JWT 토큰 발급
        String token = jwtUtil.generateToken(credentials.getUsername());
        return ResponseEntity.ok(token); // 로그인 성공 시 토큰 반환
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // 로그아웃은 클라이언트에서 토큰을 삭제하는 방식으로 처리
        return ResponseEntity.ok("로그아웃 성공");
    }

}
