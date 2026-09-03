package com.sankalpapp.serviceimpl;

import com.sankalpapp.entity.EmailOtp;
import com.sankalpapp.repository.EmailOtpRepository;
import com.sankalpapp.service.EmailService;
import com.sankalpapp.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final EmailOtpRepository emailOtpRepository;
    private final EmailService emailService;

    private static final int OTP_EXPIRY_MINUTES = 5;
    private static final int MAX_ATTEMPTS = 3;
    private static final int RESEND_COOLDOWN_SECONDS = 60;

    private final SecureRandom secureRandom = new SecureRandom();

    @Override
    @Transactional
    public void sendOtp(String email, String purpose) {

        email = email.trim().toLowerCase();

        LocalDateTime now = LocalDateTime.now();

        emailOtpRepository.findTopByEmailAndPurposeOrderByCreatedAtDesc(
                email,
                purpose
        ).ifPresent(existingOtp -> {

            if (existingOtp.getCreatedAt()
                    .plusSeconds(RESEND_COOLDOWN_SECONDS)
                    .isAfter(now)) {

                throw new RuntimeException(
                        "Please wait before requesting another OTP"
                );
            }
        });

        // Remove previous OTP for this purpose
        emailOtpRepository.deleteByEmailAndPurpose(email, purpose);

        String otp = String.format(
                "%06d",
                secureRandom.nextInt(1_000_000)
        );

        EmailOtp emailOtp = EmailOtp.builder()
                .email(email)
                .otp(otp)
                .purpose(purpose)
                .expiresAt(now.plusMinutes(OTP_EXPIRY_MINUTES))
                .verified(false)
                .attempts(0)
                .createdAt(now)
                .build();

        emailOtpRepository.save(emailOtp);

        String subject = "Your OTP Verification Code";

        String body =
                "Your OTP is: " + otp
                        + "\n\n"
                        + "This OTP is valid for "
                        + OTP_EXPIRY_MINUTES
                        + " minutes."
                        + "\n\n"
                        + "Please do not share this OTP with anyone.";

        emailService.sendEmail(email, subject, body);
    }

    @Override
    @Transactional
    public boolean verifyOtp(
            String email,
            String otp,
            String purpose) {

        email = email.trim().toLowerCase();

        EmailOtp emailOtp =
                emailOtpRepository
                        .findTopByEmailAndPurposeOrderByCreatedAtDesc(
                                email,
                                purpose
                        )
                        .orElseThrow(() ->
                                new RuntimeException("OTP not found")
                        );

        if (emailOtp.isVerified()) {
            throw new RuntimeException("OTP has already been used");
        }

        if (emailOtp.getExpiresAt()
                .isBefore(LocalDateTime.now())) {

            throw new RuntimeException("OTP has expired");
        }

        if (emailOtp.getAttempts() >= MAX_ATTEMPTS) {

            throw new RuntimeException(
                    "Maximum OTP attempts exceeded"
            );
        }

        if (!emailOtp.getOtp().equals(otp)) {

            emailOtp.setAttempts(
                    emailOtp.getAttempts() + 1
            );

            emailOtpRepository.save(emailOtp);

            throw new RuntimeException("Invalid OTP");
        }

        emailOtp.setVerified(true);

        emailOtpRepository.save(emailOtp);

        return true;
    }
}