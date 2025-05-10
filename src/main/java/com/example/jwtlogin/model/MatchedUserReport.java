package com.example.jwtlogin.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor

public class MatchedUserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //신고자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id")
    private User reporter;

    //신고대상 유저(매칭되었던 상대)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_id")
    private User reportedUser;

    private String reason;

    @Column(length = 1000)
    private String details;

    private String status = "PENDING";

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public MatchedUserReport(User reporter, User reportedUser, String reason, String details){
        this.reporter = reporter;
        this.reportedUser = reportedUser;
        this.reason = reason;
        this.details = details;
        this.status = "PENDING";

    }

    public void updateStatus(String status) {
        this.status = status;
    }



}
