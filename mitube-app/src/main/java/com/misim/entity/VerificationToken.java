package com.misim.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "verification_tokens")
@NoArgsConstructor
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne
    @JoinColumn(name = "SMS_VERIFICATION_ID")
    private SmsVerification smsVerification;

    @Builder
    public VerificationToken(User user, SmsVerification smsVerification) {
        this.user = user;
        this.smsVerification = smsVerification;
    }
}