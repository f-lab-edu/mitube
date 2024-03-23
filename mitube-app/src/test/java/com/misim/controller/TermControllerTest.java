package com.misim.controller;

import com.misim.controller.model.Response.TermDetailResponse;
import com.misim.controller.model.Response.TermListResponse;
import com.misim.controller.model.Response.TermResponse;
import com.misim.service.TermService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TermController.class)
@WithMockUser
class TermControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TermService termService;

    @Test
    public void testGetTermsByMocking() throws Exception {

        // mock 객체
        TermListResponse mockResponse = TermListResponse.builder()
                .termResponseList(Collections.singletonList(TermResponse.builder()
                        .title("Sample Term")
                        .isRequired(true)
                        .build()))
                .build();

        given(termService.getAllTerms()).willReturn(mockResponse);

        // 실행 결과 확인
        ResultActions actions = mockMvc.perform(get("/terms/agree")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.termResponseList[0].title").value("Sample Term"))
                .andExpect(jsonPath("$.body.termResponseList[0].isRequired").value(true));
    }

    @Test
    public void testGetTermPolicyByMocking() throws Exception {

        // mock 객체
        TermDetailResponse mockResponse = TermDetailResponse.detailBuidler()
                .title("Sample TermDetailResponse")
                .content("Sample TermDetailResponse's Contents")
                .isRequired(true)
                .build();

        given(termService.getTermByTitle("Sample TermDetailResponse")).willReturn(mockResponse);

        // 실행 결과 확인
        ResultActions actions = mockMvc.perform(get("/terms/policy")
                        .param("title", "Sample TermDetailResponse")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.title").value("Sample TermDetailResponse"))
                .andExpect(jsonPath("$.body.content").value("Sample TermDetailResponse's Contents"))
                .andExpect(jsonPath("$.body.isRequired").value(true));
    }

    @Test
    @DisplayName("파라미터인 title의 값이 없는 경우")
    public void testGetTermPolicyNotFoundTermsByNoTitle() throws Exception {

        // 실행 결과 확인
        mockMvc.perform(get("/terms/policy")
                        .param("title", "")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("파라미터인 title로 term을 찾을 수 없는 경우")
    public void testGetTermPolicyNotFoundTerms() throws Exception {

        // mock 객체
        TermDetailResponse mockResponse = TermDetailResponse.detailBuidler()
                .title("Sample TermDetailResponse")
                .content("Sample TermDetailResponse's Contents")
                .isRequired(true)
                .build();

        given(termService.getTermByTitle("Sample TermDetailResponse")).willReturn(mockResponse);

        // 실행 결과 확인
        mockMvc.perform(get("/terms/policy")
                        .param("title", "Unknown TermDetailResponse")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}
