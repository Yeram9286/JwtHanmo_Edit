package com.example.jwtlogin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class PostReportResponse {

    //게시글 신고 결과 응답

    private Long reportId; //신고 ID
    private Long postId; // 신고된 게시글 ID
    private String reportUsername; // 신고자 사용자명
    private String reason; //신고사유
    private String status; //신고상태 (pending, resovled)
    private String createdAt; //신고 시각


}
