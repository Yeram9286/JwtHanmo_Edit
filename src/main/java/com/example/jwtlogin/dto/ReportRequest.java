package com.example.jwtlogin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportRequest {
    private Long PostId;
    private String reason;
    private String details;

}


