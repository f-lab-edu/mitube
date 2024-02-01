package com.misim.repository;

import com.misim.entity.SmsVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsVerificationRespository extends JpaRepository<SmsVerification, Long> {

    SmsVerification findTopByPhoneNumberAndVerificationCodeOrderByExpiryDate(String phoneNumber, String verificationCode);
}