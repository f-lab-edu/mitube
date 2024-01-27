package com.misim.service;

import com.misim.controller.model.UserDto;
import com.misim.entity.User;
import com.misim.entity.VerificationToken;
import com.misim.exception.MitubeException;
import com.misim.exception.MitubeErrorCode;
import com.misim.repository.UserRepository;
import com.misim.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TermAgreementService termAgreementService;
    private final VerificationTokenRepository tokenRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    
    public void registerUser(UserDto userDto, String url) {

        // 닉네임 중복 확인
        if (userRepository.existsByNickname(userDto.getNickname())) {
            throw new MitubeException(MitubeErrorCode.EXIST_NICKNAME);
        }


        // 이메일 중복 확인
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new MitubeException(MitubeErrorCode.EXIST_EMAIL);
        }


        // userDto -> user로 변환하여 db에 저장 (비밀번호 암호화)
        User user = User.builder()
                .nickname(userDto.getNickname())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .email(userDto.getEmail())
                .build();

        userRepository.save(user);

        // 이메일 전송
        sendVerificationEmail(user, url);

        // 약관 동의
        List<Boolean> agreeList = new ArrayList<>(Arrays.asList(userDto.isAgreeMandatoryTerm1(), userDto.isAgreeMandatoryTerm2(), userDto.isAgreeOptionalTerm1(), userDto.isAgreeOptionalTerm2()));

        termAgreementService.setTermAgreements(user, agreeList);
    }

    private void sendVerificationEmail(User user, String url) {

        String token = UUID.randomUUID().toString();

        tokenRepository.save(new VerificationToken(token, user));

        // 이메일 설정
        String toAddress = user.getEmail();
        String fromAddress = "hongkildong990@gmail.com";
        String subject = "Please verify your account!";
        String content = "Please click the link below to verify your account:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY ACCOUNT</a></h3>";

        String verifyURL = url + "/verifyAccount?token=" + token;

        content = content.replace("[[URL]]", verifyURL);

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(toAddress);
        email.setSubject(subject);
        email.setFrom(fromAddress);
        email.setText(content);

        mailSender.send(email);
    }

    public void verifyAccount(String token) {

        VerificationToken verificationToken = tokenRepository.findByToken(token);
        if (verificationToken == null) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_TOKEN);
        }

        User user = verificationToken.getUser();
        LocalDateTime current = LocalDateTime.now();
        if (current.isAfter(verificationToken.getExpiryDate())) {
            throw new MitubeException(MitubeErrorCode.EXPIRED_TOKEN);
        }

        user.setEnabled(true);
        userRepository.save(user);
    }
}
