package com.misim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misim.controller.model.Request.CreateVideoRequest;
import com.misim.service.VideoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(VideoController.class)
@WithMockUser
class VideoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VideoService videoService;

    @Test
    void uploadVideosByMocking() throws Exception {

        // mock 객체
        MockMultipartFile file = new MockMultipartFile("file", "test file".getBytes());

        String mockResponse = "1";

        given(videoService.uploadVideos(file)).willReturn(mockResponse);

        // 실행 결과 확인
        ResultActions actions = mockMvc.perform(multipart(HttpMethod.POST,"/videos/upload")
                        .file(file)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());

        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.id").value("1"));
    }

    @Test
    void createVideosByMocking() throws Exception {

        // mock 객체
        CreateVideoRequest mockCreateVideoRequest = new CreateVideoRequest();
        mockCreateVideoRequest.setTitle("test");
        mockCreateVideoRequest.setDescription("test video");
        mockCreateVideoRequest.setNickname("hongkildong");
        mockCreateVideoRequest.setToken("MQ==");
        mockCreateVideoRequest.setCategoryId(1);

        doNothing().when(videoService).createVideos(mockCreateVideoRequest);

        // 실행 결과 확인
        mockMvc.perform(post("/videos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockCreateVideoRequest))
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}