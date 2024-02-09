package com.misim.repository;

import com.misim.entity.SmsVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SmsVerificationRepository extends JpaRepository<SmsVerification, Long> {

    SmsVerification findSmsVerificationByPhoneNumber(String phoneNumber);
}
