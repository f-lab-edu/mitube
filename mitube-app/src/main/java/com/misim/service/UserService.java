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


    // ******* 수정 필요 ********
    // try-catch에서 exception 발생했을 때, 단순히 롤백만 하는게 아니라 뭔가 더 있으면 좋겠는데
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
            List<Boolean> agreements = new ArrayList<>(Arrays.asList(userDto.isAgreeRequiredTerm1(), userDto.isAgreeRequiredTerm2(), userDto.isAgreeOptionalTerm1(), userDto.isAgreeOptionalTerm2()));

            termAgreementService.associateTermAgreements(user, agreements);

            // 본인 인증 정보와 유저 정보 연결
            verificationTokenService.associateVerificationToken(user, userDto.getToken());

            transactionManager.commit(status);
        } catch (Exception e) {
            transactionManager.rollback(status);
        }
    }

    // ******* 수정 필요 ********
    // 1. mail 전송에서 오류가 발생한 경우에 대한 예외 처리 고민 필요
    // 2. try-catch에서 exception 발생했을 때, 단순히 롤백만 하는게 아니라 뭔가 더 있으면 좋겠는데
    public void resetUserPassword(String nickname, String token) {

        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            User user = verificationTokenService.findUserByToken(token);

            if (nickname.equals(user.getNickname())) {
                String randomPassword = TemporaryPasswordGenerator.generateRandomPassword();
                user.setPassword(passwordEncoder.encode(randomPassword));

                userRepository.save(user);

                sendTemporaryPasswordByEmail(user.getEmail(), user.getPassword());

                transactionManager.commit(status);
            }
        } catch (Exception e) {
            transactionManager.rollback(status);
        }

    }

    private void sendTemporaryPasswordByEmail(String toAddress, String password) {

        String fromAddress = "hongkildong990@gmail.com";
        String subject = "Temporary Password Notification";
        String content = "Hello,\n\nWe are sending you a temporary password.\n\nTemporary Password: " + password + "\n\nPlease be sure to change your password after logging in.\n\nThank you.";


        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(toAddress);
        mail.setFrom(fromAddress);
        mail.setSubject(subject);
        mail.setText(content);

        mailSender.send(mail);
    }
}
