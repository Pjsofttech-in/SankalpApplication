package com.sankalpapp.dto.Response;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {

    private Long userId;
    private String fullName;
    private String email;
    private String role;
    private String token;
    private String message;
    private Map<String, Object> data;
}