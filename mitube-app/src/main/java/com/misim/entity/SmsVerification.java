package com.misim.entity;

import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class SmsVerification {
    private static final long EXPIRATION_MINUTES = 3;
    private static final long NUMBER_OF_FAILURES = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phoneNumber;

    @Setter
    private String verificationCode;

    private LocalDateTime expiryDate;

    private Integer currentFailures;

    private Boolean isVerified;

    @Builder
    public SmsVerification(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        this.isVerified = false;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    public void setExpiryDate(LocalDateTime now) {
        this.expiryDate = now.plusMinutes(EXPIRATION_MINUTES);
    }

    public void setCurrentFailures(Integer currentFailures) {
        if (currentFailures + 1 < NUMBER_OF_FAILURES) {
            this.currentFailures = currentFailures + 1;
        } else {
            throw new MitubeException(MitubeErrorCode.MAX_FAILURES);
        }
    }
}
