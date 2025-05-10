package com.example.jwtlogin.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // 기본 생성자는 보호된 접근으로 설정
@AllArgsConstructor
@Builder  // 빌더 패턴 추가
public class PostReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 신고대상 글
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    // 신고자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    private String reason;

    @Column(length = 1000)
    private String details;

    private LocalDateTime createdAt;

    private String status;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = "PENDING";  // 상태 초기값 설정
    }

    // 상태 업데이트 메서드
    public void updateStatus(String status) {
        this.status = status;
    }
}
