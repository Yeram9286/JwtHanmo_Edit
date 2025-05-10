package com.example.jwtlogin.service;

import com.example.jwtlogin.dto.PostResponse;
import com.example.jwtlogin.model.Post;
import com.example.jwtlogin.model.User;
import com.example.jwtlogin.repository.PostReportRepository;
import com.example.jwtlogin.repository.PostRepository;
import com.example.jwtlogin.repository.UserRepository;
import com.example.jwtlogin.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostReportRepository postReportRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // 신고한 게시글 제외 + isAuthor 포함 게시글 목록 반환
    public List<PostResponse> getPostsExcludingReportedByUser(String token) {
        String username = jwtUtil.extractUsername(token);

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        List<Long> reportedPostIds = postReportRepository.findAllByReporter(currentUser)
                .stream()
                .map(report -> report.getPost().getId())
                .collect(Collectors.toList());

        return postRepository.findAll().stream()
                .filter(post -> !reportedPostIds.contains(post.getId()))
                .map(post -> new PostResponse(
                        post.getId(),
                        post.getTitle(),
                        post.getContent(),
                        post.getAuthor().getUsername(),
                        post.getAuthor().getUsername().equals(username)
                ))
                .collect(Collectors.toList());
    }

    // 게시글 작성
    public PostResponse createPost(String token, String title, String content) {
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자 없음"));

        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setAuthor(user);

        //저장
        Post savedPost = postRepository.save(post);

        return new PostResponse(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getContent(),
                savedPost.getAuthor().getUsername(),
                true
        );
    }

    // 게시물 수정
    public PostResponse updatePost(Long postId, String token, String title, String content) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음"));

        String username = jwtUtil.extractUsername(token);

        if (!post.getAuthor().getUsername().equals(username)) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다");
        }

        post.setTitle(title);
        post.setContent(content);

       Post updatedPost = postRepository.save(post);

       return new PostResponse(
               updatedPost.getId(),
               updatedPost.getTitle(),
               updatedPost.getContent(),
               updatedPost.getAuthor().getUsername(),
               true
       );
    }

    // 게시물 삭제
    public void deletePost(Long postId, String token) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물 없음"));

        String username = jwtUtil.extractUsername(token);

        if (!post.getAuthor().getUsername().equals(username)) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }
}
