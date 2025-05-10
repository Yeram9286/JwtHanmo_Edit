package com.example.jwtlogin.controller;

import com.example.jwtlogin.dto.PostResponse;
import com.example.jwtlogin.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api") // 엔드포인트 앞에 /api를 붙이고 싶다면
public class PostController {

    @Autowired
    private PostService postService;

    // 게시글 목록 조회
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponse>> getPosts(@RequestHeader("Authorization") String token) {
        String pureToken = token.replace("Bearer ", "");
        List<PostResponse> posts = postService.getPostsExcludingReportedByUser(pureToken);
        return ResponseEntity.ok(posts);
    }

    // 게시글 작성
    @PostMapping("/posts")
    public ResponseEntity<PostResponse> createPost(
            @RequestHeader("Authorization") String token,
            @RequestParam("title") String title,
            @RequestParam("content") String content
    ) {
        String pureToken = token.replace("Bearer ", "");
        PostResponse newPost = postService.createPost(pureToken, title, content);
        return ResponseEntity.ok(newPost);
    }

    // 게시글 수정
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @PathVariable Long postId,
            @RequestHeader("Authorization") String token,
            @RequestParam("title") String title,
            @RequestParam("content") String content
    ) {
        String pureToken = token.replace("Bearer ", "");
        PostResponse updatedPost = postService.updatePost(postId, pureToken, title, content);
        return ResponseEntity.ok(updatedPost);
    }

    // 게시글 삭제
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Void> deletePost(
            @PathVariable Long postId,
            @RequestHeader("Authorization") String token
    ) {
        String pureToken = token.replace("Bearer ", "");
        postService.deletePost(postId, pureToken);
        return ResponseEntity.noContent().build();
    }
}
