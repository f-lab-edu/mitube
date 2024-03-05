package com.misim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void signupUser() {
    }

    @Test
    void sendSMSVerificationCode() {
    }

    @Test
    void checkSMSVerificationCode() {
    }

    @Test
    void findNickname() {
    }

    @Test
    void resetPassword() {
    }

    @Test
    void changePassword() {
    }
}