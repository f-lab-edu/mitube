package com.misim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.misim.controller.model.Request.CreateVideoRequest;
import com.misim.entity.VideoFile;
import com.misim.repository.VideoFileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class VideoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VideoFileRepository videoFileRepository;

    @Test
    void uploadVideos() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "test-video.mp4", MediaType.MULTIPART_FORM_DATA_VALUE,"test video file".getBytes());

        ResultActions actions = mockMvc.perform(multipart(HttpMethod.POST,"/videos/upload")
                        .file(file)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());

        actions.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.body.id").value("MQ=="));

        Optional<VideoFile> videoFile = videoFileRepository.findById(1L);

        videoFile.ifPresent(i -> System.out.println(i.getPath()));
    }

    // 아래 테스트가 진행되려면, 유저와 업로드된 파일이 필요하다.
    // Before 어노테이션 활용해서 해결할 수 있을까?
    @Test
    void createVideos() throws Exception {

        // mock 객체
        CreateVideoRequest request = new CreateVideoRequest();
        request.setTitle("test");
        request.setDescription("test video");
        request.setNickname("hongkildong");
        request.setToken("MQ==");
        request.setCategoryId(1);

        // 실행 결과 확인
        mockMvc.perform(post("/videos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}