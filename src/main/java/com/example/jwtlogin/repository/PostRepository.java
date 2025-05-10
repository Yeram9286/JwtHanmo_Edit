package com.example.jwtlogin.repository;

import com.example.jwtlogin.model.Post;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {
}
