package com.testapplication.controller;

import com.testapplication.entity.User;
import com.testapplication.dto.Request.LoginRequest;
import com.testapplication.dto.Response.LoginResponse;
import com.testapplication.security.CustomUserDetailsService;
import com.testapplication.security.JwtUtil;
import com.testapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService customUserDetailsService;
    private final PasswordEncoder passwordEncoder;


    // Register User
    @PostMapping("/register")
    public User register(@RequestBody User user) {

        // Encrypt Password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        User user = userService.getUserByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Email"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        UserDetails userDetails =
                customUserDetailsService.loadUserByUsername(user.getEmail());

        String token = jwtUtil.generateToken(userDetails);

        return LoginResponse.builder()
                .userId(user.getId())   // ✅ Add this line
                .token(token)
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().getRoleName())
                .message("Login Successful")
                .build();
    }
}
