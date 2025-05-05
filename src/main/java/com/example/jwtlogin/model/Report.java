package com.example.jwtlogin.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor

public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long PostId;//신고대상게시물 ID
    private String reporterUsername; //신고자
    private String reason;
    private String details;
}

