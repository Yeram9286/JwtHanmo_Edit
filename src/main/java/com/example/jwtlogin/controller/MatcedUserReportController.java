package com.example.jwtlogin.controller;

import com.example.jwtlogin.Security.JwtTokenProvider;
import com.example.jwtlogin.dto.MatchedUserReportRequest;
import com.example.jwtlogin.model.MatchedUserReport;
import com.example.jwtlogin.model.User;
import com.example.jwtlogin.repository.UserRepository;
import com.example.jwtlogin.service.MatchedUserReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class MatcedUserReportController {

    @Autowired
    private MatchedUserReportService matchedUserReportService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository; // UserRepository 추가

    // 유저 신고 API(POST)
    @PostMapping("/user")
    public ResponseEntity<?> reportUser(
            @AuthenticationPrincipal User reporter, // JWT로 인증된 사용자
            @RequestBody MatchedUserReportRequest requestDto) { // DTO 타입 맞추기

        // 신고할 유저의 ID 가져오기 (JWT 인증된 유저와 매칭된 유저만 신고 됨)
        User reportedUser = userRepository.findByUsername(requestDto.getReportedUserId()) // UserRepository에 findById 사용
                .orElseThrow(() -> new IllegalArgumentException("신고할 유저를 찾을 수 없습니다."));

        // 유저 신고 처리
        MatchedUserReport userReport = matchedUserReportService.reportUser(reporter, reportedUser, requestDto);

        return ResponseEntity.ok(userReport); // 신고 성공 응답
    }
}
