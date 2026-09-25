package com.sankalpapp.controller;

import com.sankalpapp.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/test")
    public ResponseEntity<?> testEmail(
            @RequestParam String email) {

        emailService.sendEmail(
                email,
                "Test Email",
                "This is a test email from Sankalp Application."
        );

        return ResponseEntity.ok(
                "Email sent successfully"
        );
    }
}