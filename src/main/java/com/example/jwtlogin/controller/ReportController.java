package com.example.jwtlogin.controller;

import com.example.jwtlogin.dto.ReportRequest;
import com.example.jwtlogin.model.Report;
import com.example.jwtlogin.service.ReportService;
import com.example.jwtlogin.util.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.Jar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<String> reportPost(@RequestBody ReportRequest request, HttpServletResponse httpRequest) {
        String token = httpRequest.getHeader("Authorization").replace("Bearer ", "");
        String username = jwtUtil.extractUsername(token);

        reportService.reportPost(username, request);
        return ResponseEntity.ok("신고가 접수되었습니다.");
    }
}
