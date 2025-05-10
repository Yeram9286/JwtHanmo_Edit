package com.example.jwtlogin.repository;

import com.example.jwtlogin.model.PostReport;
import com.example.jwtlogin.model.User;
import com.example.jwtlogin.service.PostReportService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {
    // 사용자에 의해 신고된 모든 게시물 리스트를 가져옴
    List<PostReport> findAllByReporter(User reporter);
}
