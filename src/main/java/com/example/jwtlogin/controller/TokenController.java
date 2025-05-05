package com.example.jwtlogin.controller;

import com.example.jwtlogin.service.TokenService;
import com.example.jwtlogin.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenService tokenService;

    //리프레시 토큰-> 엑세스 토큰 재발급

}
