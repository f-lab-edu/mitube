package com.misim.service;

import com.misim.controller.model.UserDto;
import com.misim.entity.User;
import com.misim.exception.MitubeException;
import com.misim.exception.MitubeErrorCode;
import com.misim.repository.UserRepository;
import com.misim.util.TemporaryPasswordGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TermAgreementService termAgreementService;
    private final SmsService smsService;
    private final VerificationTokenService verificationTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final PlatformTransactionManager transactionManager;

    public void registerUser(UserDto userDto) {

        // 본인 인증 확인
        if (!smsService.checkVerification(userDto.getToken())) {
            throw new MitubeException(MitubeErrorCode.NOT_VERIFIED_SMS_TOKEN);
        }

        // 닉네임 중복 확인
        if (userRepository.existsByNickname(userDto.getNickname())) {
            throw new MitubeException(MitubeErrorCode.EXIST_NICKNAME);
        }
        // 이메일 중복 확인
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new MitubeException(MitubeErrorCode.EXIST_EMAIL);
        }

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            // userDto -> user로 변환 (비밀번호 암호화)
            User user = User.builder()
                    .nickname(userDto.getNickname())
                    .password(passwordEncoder.encode(userDto.getPassword()))
                    .email(userDto.getEmail())
                    .phoneNumber(userDto.getPhoneNumber())
                    .build();


            // 유저 정보 저장
            userRepository.save(user);

            // 약관 동의 정보 연결
            List<Boolean> agreeList = new ArrayList<>(Arrays.asList(userDto.isAgreeRequiredTerm1(), userDto.isAgreeRequiredTerm2(), userDto.isAgreeOptionalTerm1(), userDto.isAgreeOptionalTerm2()));

            termAgreementService.setTermAgreements(user, agreeList);

            // 본인 인증 정보와 유저 정보 연결
            verificationTokenService.setVerificationToken(user, userDto.getToken());

            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }
    }

    public void resetUserPassword(String nickname, String token) {

        User user = verificationTokenService.findUser(token);

        if (nickname.equals(user.getNickname())) {
            String randomPassword = TemporaryPasswordGenerator.generateRandomPassword();
            user.setPassword(passwordEncoder.encode(randomPassword));

            userRepository.save(user);

            sendTemporaryPasswordByEmail(user);
        }
    }

    private void sendTemporaryPasswordByEmail(User user) {

        // 이메일 설정
        String toAddress = user.getEmail();
        String fromAddress = "hongkildong990@gmail.com";
        String subject = "Temporary Password Notification";
        String content = "Hello,\n\nWe are sending you a temporary password.\n\nTemporary Password: " + user.getPassword() + "\n\nPlease be sure to change your password after logging in.\n\nThank you.";


        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(toAddress);
        email.setFrom(fromAddress);
        email.setSubject(subject);
        email.setText(content);

        mailSender.send(email);
    }
}
