package com.example.jwtlogin.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60; //1시간
    private final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7;//7일

    //Access토큰 생성
    public String generateAccessToken(String username) {
        return generateToken(username, ACCESS_TOKEN_EXPIRATION);
    }
    //Refresh 토큰 생성
    public String generateRefreshToken(String username) {
        return generateToken(username, REFRESH_TOKEN_EXPIRATION);
    }

    //공통토큰생성로직
    private String generateToken(String username, long expirationTime) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }



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
