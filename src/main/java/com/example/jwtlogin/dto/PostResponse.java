package com.example.jwtlogin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private boolean isAuthor;

    public PostResponse(Long id, String title,String content, String author, boolean isAuthor) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.isAuthor = isAuthor;
    }
}
