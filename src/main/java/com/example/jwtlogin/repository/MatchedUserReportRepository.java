package com.example.jwtlogin.repository;


import com.example.jwtlogin.model.MatchedUserReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchedUserReportRepository extends JpaRepository<MatchedUserReport, Long> {
}
