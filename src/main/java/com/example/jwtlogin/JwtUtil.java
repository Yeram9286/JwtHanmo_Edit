package com.example.jwtlogin;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private String secretKey = "mysecretkey";

    // 토큰 생성
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // 토큰에 포함될 사용자 이름
                .setIssuedAt(new Date()) // 토큰 발급 시간
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 만료시간(1시간)
                .signWith(SignatureAlgorithm.HS256, secretKey) // 서명
                .compact();
    }

    // 토큰 검증
    public boolean validateToken(String token, String username) {
        String extractedUsername = extractUsername(token);
        return (username.equals(extractedUsername) && !isTokenExpired(token));
    }

    // 토큰에서 사용자 이름 추출
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰이 만료되었는지 확인
    public boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }
}
