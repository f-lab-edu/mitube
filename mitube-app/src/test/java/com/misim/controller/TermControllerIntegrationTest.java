package com.misim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misim.controller.model.Response.TermDetailResponse;
import com.misim.entity.Term;
import com.misim.repository.TermRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class TermControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TermRepository termRepository;

    @BeforeEach
    void setUp() {
        if (!termRepository.existsById(1L)) {
            termRepository.saveAll(Arrays.asList(개인정보동의(), 광고수신동의()));
        }
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

    @Test
    void getTermsSuccess() throws Exception {
        mockMvc.perform(get("/terms/agree")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // TermControllerIntegrationTest를 실행하면, 상태코드 500이 반환된다.
    // 아래의 메소드만 실행하면, 원하는 데로 잘 동작한다.
    // 이 부분에 대한 탐구 필요.dskfjdsklsdjslkkdjfs1234125
    @Test
    void getTermPolicySuccess() throws Exception {
        mockMvc.perform(get("/terms/policy")
                        .param("title", "개인정보동의")
                        .contentType(MediaType.APPLICATION_JSON));

        Optional<Term> term = termRepository.findTermByTitleAndMaxVersion("개인정보동의");

        term.ifPresent(t -> System.out.println(t.getTitle()));
    }

    @Test
    void getTermPolicy_hasError_EmptyTitle() throws Exception {
        mockMvc.perform(get("/terms/policy")
                        .param("title", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void getTermPolicy_hasError_NotFoundTerm() throws Exception {
        mockMvc.perform(get("/terms/policy")
                        .param("title", "위치정보동의")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}