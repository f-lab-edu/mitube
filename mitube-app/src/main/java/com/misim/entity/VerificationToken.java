package com.misim.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "verification_tokens")
@NoArgsConstructor
public class VerificationToken {
    private static final long EXPIRATION_DAY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private LocalDateTime expiryDate;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.expiryDate = LocalDateTime.now().plusDays(EXPIRATION_DAY);
    }
}