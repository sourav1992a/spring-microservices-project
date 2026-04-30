package com.practice.apigateway.service.controller;

import com.practice.apigateway.service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public String login(@RequestParam String username) {
        return jwtUtil.generateToken(username);
    }
}
