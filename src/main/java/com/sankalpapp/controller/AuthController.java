package com.sankalpapp.controller;

import com.sankalpapp.dto.Request.LoginRequest;
import com.sankalpapp.dto.Response.LoginResponse;
import com.sankalpapp.entity.User;
import com.sankalpapp.security.CustomUserDetailsService;
import com.sankalpapp.security.JwtUtil;
import com.sankalpapp.service.UserService;
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
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        User user = userService.getUserByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Email"));

        System.out.println("RAW PASSWORD FROM REQUEST: [" + request.getPassword() + "]");
        System.out.println("HASH FROM DB: [" + user.getPassword() + "]");

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
