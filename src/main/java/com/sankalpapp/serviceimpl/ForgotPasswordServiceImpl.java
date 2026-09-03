package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.EmailOtp;
import com.sankalpapp.entity.User;
import com.sankalpapp.repository.EmailOtpRepository;
import com.sankalpapp.repository.ForgotPasswordService;
import com.sankalpapp.repository.UserRepository;
import com.sankalpapp.service.OtpService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ForgotPasswordServiceImpl
        implements ForgotPasswordService {

    private static final String PASSWORD_RESET =
            EmailOtp.OTP_TYPE.PASSWORD_RESET.name();

    private final UserRepository userRepository;
    private final OtpService otpService;
    private final EmailOtpRepository emailOtpRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void sendForgotPasswordOtp(String email) {

        email = email.trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));

        if (!Boolean.TRUE.equals(user.getActive())) {
            throw new RuntimeException(
                    "User account is inactive"
            );
        }

        otpService.sendOtp(
                email,
                PASSWORD_RESET
        );
    }

    @Override
    @Transactional
    public void verifyOtp(String email, String otp) {

        email = email.trim().toLowerCase();

        otpService.verifyOtp(
                email,
                otp,
                PASSWORD_RESET
        );
    }

    @Override
    @Transactional
    public void resetPassword(
            String email,
            String newPassword) {

        email = email.trim().toLowerCase();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found"
                        ));

        EmailOtp emailOtp =
                emailOtpRepository
                        .findTopByEmailAndPurposeOrderByCreatedAtDesc(
                                email,
                                PASSWORD_RESET
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "OTP verification required"
                                ));

        if (!emailOtp.isVerified()) {
            throw new RuntimeException(
                    "Please verify OTP first"
            );
        }

        if (emailOtp.getExpiresAt()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException(
                    "OTP verification has expired"
            );
        }

        user.setPassword(
                passwordEncoder.encode(newPassword)
        );

        userRepository.save(user);

        // OTP should not be usable again
        emailOtpRepository.delete(emailOtp);
    }
}