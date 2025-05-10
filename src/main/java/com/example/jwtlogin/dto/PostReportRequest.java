package com.example.jwtlogin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostReportRequest {
    private String reporter;
    private Long PostId;
    private String reason;
    private String details;

}
