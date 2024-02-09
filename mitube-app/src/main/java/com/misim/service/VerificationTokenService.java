package com.misim.service;

import com.misim.entity.SmsVerification;
import com.misim.entity.User;
import com.misim.entity.VerificationToken;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.SmsVerificationRepository;
import com.misim.repository.VerificationTokenRepository;
import com.misim.util.Base64Convertor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final SmsVerificationRepository smsVerificationRepository;


    public void associateVerificationToken(User user, String token) {

        SmsVerification smsVerification = smsVerificationRepository
                .findById(Base64Convertor.decode(token))
                .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_SMS_TOKEN));

        VerificationToken verificationToken = VerificationToken.builder()
                .user(user)
                .smsVerification(smsVerification)
                .build();

        verificationTokenRepository.save(verificationToken);
    }

    public User findUserByToken(String token) {

        Long id = Base64Convertor.decode(token);

        if (!smsVerificationRepository.existsById(id)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_SMS_TOKEN);
        }

        VerificationToken verificationToken = verificationTokenRepository.findVerificationTokenBySmsVerificationId(id);

        return verificationToken.getUser();
    }

    public String findUserNicknameByToken(String token) {

        Long id = Base64Convertor.decode(token);

        if (!smsVerificationRepository.existsById(id)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_SMS_TOKEN);
        }

        VerificationToken verificationToken = verificationTokenRepository.findVerificationTokenBySmsVerificationId(id);

        return verificationToken.getUser().getNickname();
    }
}
