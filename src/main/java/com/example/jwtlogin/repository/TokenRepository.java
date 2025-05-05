package com.example.jwtlogin.repository;

import com.example.jwtlogin.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByUserUsername(String username);
}
