package com.misim.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.misim.controller.model.Request.*;
import com.misim.controller.model.Response.FindNicknameResponse;
import com.misim.controller.model.Response.VerifySMSResponse;
import com.misim.entity.SmsVerification;
import com.misim.entity.Term;
import com.misim.entity.User;
import com.misim.entity.VerificationToken;
import com.misim.repository.SmsVerificationRepository;
import com.misim.repository.TermRepository;
import com.misim.repository.UserRepository;
import com.misim.util.Base64Convertor;
import com.misim.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SmsVerificationRepository smsVerificationRepository;

    @Autowired
    private TermRepository termRepository;

    @Autowired
    private UserRepository userRepository;

    SmsVerification 본인인증(String s) {
        return SmsVerification.builder()
                .phoneNumber("0101234567" + s)
                .verificationCode("123456")
                .build();
    }

    Term 개인정보동의() {
        return Term.builder()
                .title("개인정보동의")
                .content("개인정보동의")
                .isRequired(true)
                .version(1)
                .termGroup(1)
                .build();
    }

    Term 광고수신동의() {
        return Term.builder()
                .title("광고수신동의")
                .content("광고수신동의")
                .isRequired(false)
                .version(1)
                .termGroup(2)
                .build();
    }

    SignUpUserRequest 유저등록(String phoneNumber) {
        SignUpUserRequest mockRequest = new SignUpUserRequest();
        mockRequest.setEmail("hongkildong@example.com");
        mockRequest.setPassword("Qwer1234%");
        mockRequest.setConfirmPassword("Qwer1234%");
        mockRequest.setNickname("hongkildong");
        mockRequest.setPhoneNumber(phoneNumber);
        SmsVerification smsVerification = smsVerificationRepository.findSmsVerificationByPhoneNumber(phoneNumber);
        mockRequest.setToken(Base64Convertor.encode(smsVerification.getId()));
        mockRequest.setCheckedTermTitles(Arrays.asList("개인정보동의", "광고수신동의"));

        return mockRequest;
    }

    @Test
    void sendSMSVerificationCode() throws Exception {

        // mock 객체
        SendSMSRequest mockRequest = new SendSMSRequest();
        mockRequest.setPhoneNumber("01012345678");

        // 실행 결과 확인
        mockMvc.perform(post("/users/sendVerificationBySMS")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void checkSMSVerificationCode() throws Exception {

        smsVerificationRepository.save(본인인증("1"));

        // mock 객체
        VerifySMSRequest mockRequest = new VerifySMSRequest();
        mockRequest.setPhoneNumber("01012345671");

        // 수정 필요
        SmsVerification smsVerification = smsVerificationRepository.findSmsVerificationByPhoneNumber("01012345671");
        mockRequest.setCode(smsVerification.getVerificationCode());

        mockRequest.setRequestTime(TimeUtil.formatLocalDateTimeNow());

        // 실행 결과 확인
        ResultActions actions = mockMvc.perform(post("/users/verifyAccountSMS")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest))
                        .with(csrf()))
                .andExpect(status().isOk());

        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.token").value(smsVerification.getId()));
    }

    @Test
    void signupUser() throws Exception {

        SmsVerification 본인인증 = 본인인증("2");
        본인인증.setVerified(true);

        smsVerificationRepository.save(본인인증);

        termRepository.saveAll(Arrays.asList(개인정보동의(), 광고수신동의()));

        // mock 객체
        SignUpUserRequest mockRequest = 유저등록(본인인증.getPhoneNumber());

        // 실행 결과 확인
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void findNickname() throws Exception {

        User 철수 = User.builder()
                .nickname("철수")
                .build();

        SmsVerification 본인인증 = 본인인증("3");
        본인인증.setVerified(true);
        smsVerificationRepository.save(본인인증);

        VerificationToken 인증토큰 = VerificationToken.builder()
                .user(철수)
                .smsVerification(본인인증)
                .build();

        철수.setVerificationToken(인증토큰);

        userRepository.save(철수);
        
        // mock 객체
        FindNicknameRequest mockRequest = new FindNicknameRequest();
        SmsVerification smsVerification = smsVerificationRepository.findSmsVerificationByPhoneNumber(본인인증.getPhoneNumber());
        mockRequest.setToken(Base64Convertor.encode(smsVerification.getId()));

        // 실행 결과 확인
        ResultActions actions = mockMvc.perform(post("/users/nickname/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest))
                        .with(csrf()))
                .andExpect(status().isOk());

        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.nickname").value(철수.getNickname()));
    }

    @Test
    void resetPassword() throws Exception {

        User 영희 = User.builder()
                .nickname("영희")
                .build();

        SmsVerification 본인인증 = 본인인증("4");
        본인인증.setVerified(true);
        smsVerificationRepository.save(본인인증);

        VerificationToken 인증토큰 = VerificationToken.builder()
                .user(영희)
                .smsVerification(본인인증)
                .build();

        영희.setVerificationToken(인증토큰);

        userRepository.save(영희);

        // mock 객체
        ResetPasswordRequest mockRequest = new ResetPasswordRequest();
        mockRequest.setNickname(영희.getNickname());
        SmsVerification smsVerification = smsVerificationRepository.findSmsVerificationByPhoneNumber(본인인증.getPhoneNumber());
        mockRequest.setCode(Base64Convertor.encode(smsVerification.getId()));

        // 실행 결과 확인
        mockMvc.perform(post("/users/help/resetPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest))
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}