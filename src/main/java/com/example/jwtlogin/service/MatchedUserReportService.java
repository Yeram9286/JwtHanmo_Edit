package com.example.jwtlogin.service;


import com.example.jwtlogin.dto.MatchedUserReportRequest;
import com.example.jwtlogin.model.MatchedUserReport;
import com.example.jwtlogin.model.User;
import com.example.jwtlogin.repository.MatchedUserReportRepository;
import com.example.jwtlogin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchedUserReportService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MatchedUserReportRepository matchedUserReportRepository;

    //유저간 신고기능

    @Transactional
    public MatchedUserReport reportUser(User reporter, User reportedUser, MatchedUserReportRequest requestDto){

        //신고대상 유저 유효 체크
        if (reportedUser == null){
            throw new IllegalArgumentException("신고할 유저가 존재하지 않습니다.");

        }

        //유저 신고 생성
        MatchedUserReport userReport = new MatchedUserReport(
                reporter,
                reportedUser,
                requestDto.getReason(),
                requestDto.getDetails()
        );

        //신고저장
        return matchedUserReportRepository.save(userReport);
    }

}
