package com.misim.repository;

import com.misim.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    VerificationToken findVerificationTokenBySmsVerificationId(Long smsVerificationId);

    Boolean existsVerificationTokenBySmsVerificationId(Long smsVerificationId);
}
