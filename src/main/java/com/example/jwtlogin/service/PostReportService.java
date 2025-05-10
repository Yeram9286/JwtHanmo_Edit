package com.example.jwtlogin.service;

import com.example.jwtlogin.dto.PostReportRequest;
import com.example.jwtlogin.model.PostReport;
import com.example.jwtlogin.model.Post;
import com.example.jwtlogin.model.User;
import com.example.jwtlogin.repository.PostRepository;
import com.example.jwtlogin.repository.PostReportRepository;
import com.example.jwtlogin.repository.UserRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Getter
@Service
public class PostReportService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostReportRepository postReportRepository;

    @Autowired
    private PostRepository postRepository;

    public void reportPost(String reporterUsername, PostReportRequest request){
        // postId로 Post 객체를 가져옴.
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Post ID"));

        // reporterUsername으로 User 객체를 가져오기
        User reporter = userRepository.findByUsername(reporterUsername)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Reporter Username"));

        // PostReport 객체를 빌더 패턴으로 생성
        PostReport postReport = PostReport.builder()
                .post(post)  // Post 객체 설정
                .reporter(reporter)  // Post에 있는 reporter 설정
                .reason(request.getReason())
                .details(request.getDetails())
                .build();

        // 신고 정보를 저장
        postReportRepository.save(postReport);
    }
}
