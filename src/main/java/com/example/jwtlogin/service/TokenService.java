package com.example.jwtlogin.service;

import com.example.jwtlogin.model.Token;
import com.example.jwtlogin.model.User;
import com.example.jwtlogin.repository.TokenRepository;
import com.example.jwtlogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    // 엑세스 + 리프레시 토큰 저장
    @Transactional
    public void saveToken(String username, String accessToken, String refreshToken) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Token token = new Token();
        token.setUser(user);
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);

        tokenRepository.save(token);
    }

    // 엑세스 + 리프레시 토큰 갱신
    @Transactional
    public void updateToken(String username, String accessToken, String refreshToken) {
        Token token = tokenRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);

        tokenRepository.save(token);
    }

    // 리프레시 토큰만 갱신
    @Transactional
    public void updateRefreshToken(String username, String refreshToken) {
        Token token = tokenRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        token.setRefreshToken(refreshToken);
        tokenRepository.save(token);
    }

    // 액세스 토큰만 갱신
    @Transactional
    public void updateAccessToken(String username, String accessToken) {
        Token token = tokenRepository.findByUserUsername(username)
                .orElseThrow(() -> new RuntimeException("Token not found"));

        token.setAccessToken(accessToken);
        tokenRepository.save(token);
    }

    // 사용자 이름으로 토큰 조회
    public Optional<Token> getTokensByUsername(String username) {
        return tokenRepository.findByUserUsername(username);
    }
}
