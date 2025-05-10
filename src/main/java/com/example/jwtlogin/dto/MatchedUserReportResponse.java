package com.example.jwtlogin.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MatchedUserReportResponse {

    private Long reportId;
    private String reportedUsername;
    private String reason;
    private String details;
    private String status;
    private LocalDateTime createdAt;
}
