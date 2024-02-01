package com.misim.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class SmsVerification {
    private static final long EXPIRATION_MINUTES = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    private String verificationCode;

    private LocalDateTime expiryDate;

    private Boolean isVerified;

    @Builder
    public SmsVerification(String phoneNumber, String verificationCode) {
        this.phoneNumber = phoneNumber;
        this.verificationCode = verificationCode;
        this.expiryDate = LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES);
        this.isVerified = false;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }
}
