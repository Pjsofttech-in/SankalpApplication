package com.sankalpapp.controller;

import com.sankalpapp.dto.request.ForgotPasswordRequest;
import com.sankalpapp.dto.request.ResetPasswordRequest;
import com.sankalpapp.dto.request.VerifyForgotPasswordOtpRequest;
import com.sankalpapp.repository.ForgotPasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request) {

        forgotPasswordService.sendForgotPasswordOtp(
                request.getEmail()
        );

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "OTP sent successfully"
                )
        );
    }

    @PostMapping("/verify-forgot-password-otp")
    public ResponseEntity<?> verifyOtp(
            @Valid @RequestBody
            VerifyForgotPasswordOtpRequest request) {

        forgotPasswordService.verifyOtp(
                request.getEmail(),
                request.getOtp()
        );

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "OTP verified successfully"
                )
        );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(
            @Valid @RequestBody ResetPasswordRequest request) {

        forgotPasswordService.resetPassword(
                request.getEmail(),
                request.getNewPassword()
        );

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "Password reset successfully"
                )
        );
    }
}