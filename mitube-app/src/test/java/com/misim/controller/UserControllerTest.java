package com.misim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misim.controller.model.Request.*;
import com.misim.controller.model.Response.FindNicknameResponse;
import com.misim.controller.model.Response.VerifySMSResponse;
import com.misim.service.SmsService;
import com.misim.service.UserService;
import com.misim.service.VerificationTokenService;
import com.misim.util.TimeUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(UserController.class)
@WithMockUser
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private SmsService smsService;

    @MockBean
    private VerificationTokenService verificationTokenService;


    @Test
    void signupUser() throws Exception {

        // mock 객체
        SignUpUserRequest mockRequest = new SignUpUserRequest();
        mockRequest.setEmail("hongkildong@example.com");
        mockRequest.setPassword("Qwer1234%");
        mockRequest.setConfirmPassword("Qwer1234%");
        mockRequest.setNickname("hongkildong");
        mockRequest.setPhoneNumber("01012345678");
        mockRequest.setToken("MQ==");

        doNothing().when(userService).registerUser(mockRequest);

        // 실행 결과 확인
        mockMvc.perform(post("/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void sendSMSVerificationCode() throws Exception {

        // mock 객체
        SendSMSRequest mockRequest = new SendSMSRequest();
        mockRequest.setPhoneNumber("01012345678");

        doNothing().when(smsService).sendSMS(mockRequest.getPhoneNumber());

        // 실행 결과 확인
        mockMvc.perform(post("/users/sendVerificationBySMS")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void checkSMSVerificationCode() throws Exception {

        // mock 객체
        VerifySMSRequest mockRequest = new VerifySMSRequest();
        mockRequest.setPhoneNumber("01012345678");
        mockRequest.setCode("123456");
        LocalDateTime mockCurrent = TimeUtil.getNow();
        mockRequest.setRequestTime(TimeUtil.formatLocalDateTime(mockCurrent));

        VerifySMSResponse mockResponse = new VerifySMSResponse();
        mockResponse.setToken("MQ==");

        given(smsService.matchSMS(mockRequest.getPhoneNumber(), mockRequest.getCode(), mockCurrent)).willReturn(mockResponse);

        // 실행 결과 확인
        ResultActions actions = mockMvc.perform(post("/users/verifyAccountSMS")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest))
                        .with(csrf()))
                .andExpect(status().isOk());

        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.token").value("MQ=="));
    }

    @Test
    void findNickname() throws Exception {

        // mock 객체
        FindNicknameRequest mockRequest = new FindNicknameRequest();
        mockRequest.setToken("MQ==");

        FindNicknameResponse mockResponse = new FindNicknameResponse();
        mockResponse.setNickname("hongkildong");

        given(verificationTokenService.findUserNicknameByToken(mockRequest.getToken())).willReturn(mockResponse);

        // 실행 결과 확인
        ResultActions actions = mockMvc.perform(post("/users/nickname/find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest))
                        .with(csrf()))
                .andExpect(status().isOk());

        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.nickname").value("hongkildong"));
    }

    @Test
    void resetPassword() throws Exception {

        // mock 객체
        ResetPasswordRequest mockRequest = new ResetPasswordRequest();
        mockRequest.setNickname("hongkildong");
        mockRequest.setCode("MQ==");

        doNothing().when(userService).resetUserPassword(mockRequest.getNickname(), mockRequest.getCode());

        // 실행 결과 확인
        mockMvc.perform(post("/users/help/resetPassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockRequest))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void changePassword() {
    }
}