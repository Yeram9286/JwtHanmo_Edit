package com.example.jwtlogin.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


@Getter
public class MatchedUserReportRequest {

    @NotNull(message = "신고 대상 유저는 필수입니다.")
    private String reportedUserId;

    @NotBlank(message = "신고 사유는 필수입니다.")
    private String reason;

    private String details;
}

