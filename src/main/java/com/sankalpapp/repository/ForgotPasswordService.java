package com.sankalpapp.repository;

public interface ForgotPasswordService {

    void sendForgotPasswordOtp(String email);

    void verifyOtp(String email, String otp);

    void resetPassword(String email, String newPassword);
}