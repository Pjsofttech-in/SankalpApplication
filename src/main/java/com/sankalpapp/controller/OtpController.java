package com.sankalpapp.controller;

import com.sankalpapp.dto.request.SendOtpRequest;
import com.sankalpapp.dto.request.VerifyOtpRequest;
import com.sankalpapp.entity.EmailOtp;
import com.sankalpapp.service.OtpService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.sankalpapp.entity.EmailOtp.OTP_TYPE.PASSWORD_RESET;

@RestController
@RequestMapping("/api/otp")
@RequiredArgsConstructor
public class OtpController {

    private final OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<?> sendOtp(
            @Valid @RequestBody SendOtpRequest request) {

        otpService.sendOtp(request.getEmail(), PASSWORD_RESET.toString());

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "OTP sent successfully"
                )
        );
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request) {

        otpService.verifyOtp(
                request.getEmail(),
                request.getOtp(),
                PASSWORD_RESET.toString()
        );

        return ResponseEntity.ok(
                Map.of(
                        "message",
                        "OTP verified successfully",
                        "verified",
                        true
                )
        );
    }
}