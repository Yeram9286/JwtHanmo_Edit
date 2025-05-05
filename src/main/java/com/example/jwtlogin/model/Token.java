package com.example.jwtlogin.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User와 연관관계 매핑
    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private User user;

    @Column(nullable = false, length = 500)
    private String accessToken;

    @Column(nullable = false, length = 500)
    private String refreshToken;
}
