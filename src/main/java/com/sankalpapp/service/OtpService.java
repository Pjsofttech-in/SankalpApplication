package com.sankalpapp.service;

public interface OtpService {

    void sendOtp(String email, String purpose);

    boolean verifyOtp(String email, String otp, String purpose);
}