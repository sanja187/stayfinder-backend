package com.stayfinder.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.stayfinder.repository.UserRepository;
import com.stayfinder.security.JwtUtil;
import com.stayfinder.model.User;
import com.stayfinder.dto.AuthResponse;
import com.stayfinder.dto.LoginRequest;
import com.stayfinder.dto.RegisterRequest;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    

    public User register(RegisterRequest request) {

    	 if(userRepository.existsByEmail(request.getEmail())){
    	        throw new RuntimeException("Email already exists");
    	    }
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());

        
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setPhone(request.getPhone());
        user.setRole(request.getRole());

        return userRepository.save(user);
    }


    public AuthResponse login(LoginRequest request){

        Optional<User> optionalUser = userRepository.findByEmail(request.getEmail());

        if(optionalUser.isEmpty()){
            throw new RuntimeException("User not found");
        }

        User user = optionalUser.get();

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getRole().name());

        return new AuthResponse(token, user.getRole().name(),user.getName());
    }
    




}
