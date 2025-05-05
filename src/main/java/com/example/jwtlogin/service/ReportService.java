package com.example.jwtlogin.service;

import com.example.jwtlogin.dto.ReportRequest;
import com.example.jwtlogin.model.Report;
import com.example.jwtlogin.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.RepositoryType;
import org.springframework.stereotype.Service;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    public void reportPost(String reporterUsername, ReportRequest request){
        Report report = new Report();
        report.setPostId(request.getPostId());
        report.setReporterUsername(reporterUsername);
        report.setReason(request.getReason());
        report.setDetails(request.getDetails());

        reportRepository.save(report);
    }
}
