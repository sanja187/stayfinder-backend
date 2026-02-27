package com.stayfinder.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.stayfinder.repository.UserRepository;
import com.stayfinder.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.stayfinder.dto.AuthResponse;
import com.stayfinder.dto.LoginRequest;
import com.stayfinder.dto.RegisterRequest;
import com.stayfinder.model.User;
import com.stayfinder.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;



    @PostMapping("/register")
    public Object register(@RequestBody RegisterRequest request) {
        try {
            return userService.register(request);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", e.getMessage());
            return error;
        }
    }
    

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){
        return userService.login(request);
    }






}
