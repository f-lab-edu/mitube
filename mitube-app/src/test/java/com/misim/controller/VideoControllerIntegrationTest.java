package com.misim.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.misim.controller.model.Request.CreateVideoRequest;
import com.misim.entity.User;
import com.misim.entity.VideoFile;
import com.misim.repository.UserRepository;
import com.misim.repository.VideoFileRepository;
import com.misim.util.Base64Convertor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private UserRepository userRepository;

    @Test
    void uploadVideosSuccess() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "test-video.mp4", MediaType.MULTIPART_FORM_DATA_VALUE, "test video file".getBytes());

        ResultActions actions = mockMvc.perform(multipart(HttpMethod.POST,"/videos/upload")
                        .file(file)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().isOk());

        MvcResult result = actions
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Long videoFileId = JsonPath.read(jsonResponse, "$.body.id");

        Optional<VideoFile> videoFile = videoFileRepository.findById(videoFileId);

        videoFile.ifPresent(i -> System.out.println(i.getPath()));
    }

    @Test
    void uploadVideos_hasError_EmptyFile() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "test-video.mp4", MediaType.MULTIPART_FORM_DATA_VALUE, new byte[0]);

        mockMvc.perform(multipart(HttpMethod.POST,"/videos/upload")
                        .file(file)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createVideosSuccess() throws Exception {

        User 홍길동 = User.builder()
                .nickname("hongkildong")
                .build();

        userRepository.save(홍길동);

        VideoFile 테스트_비디오 = VideoFile.builder()
                .path("test path")
                .build();

        videoFileRepository.save(테스트_비디오);

        // mock 객체
        CreateVideoRequest request = new CreateVideoRequest();
        request.setTitle("test");
        request.setDescription("test video");
        request.setNickname("hongkildong");
        VideoFile videoFile = videoFileRepository.findByPath("test path");
        request.setToken(Base64Convertor.encode(videoFile.getId()));
        request.setCategoryId(1);

        // 실행 결과 확인
        mockMvc.perform(post("/videos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void createVideos_hasError_RequestNoTitle() throws Exception {

        // mock 객체
        CreateVideoRequest request = new CreateVideoRequest();
        request.setTitle(null);
        request.setDescription("test video");
        request.setNickname("hongkildong");
        request.setToken("token");
        request.setCategoryId(1);

        // 실행 결과 확인
        mockMvc.perform(post("/videos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createVideos_hasError_RequestNoDescription() throws Exception {

        // mock 객체
        CreateVideoRequest request = new CreateVideoRequest();
        request.setTitle("test");
        request.setDescription(null);
        request.setNickname("hongkildong");
        request.setToken("token");
        request.setCategoryId(1);

        // 실행 결과 확인
        mockMvc.perform(post("/videos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createVideos_hasError_RequestNoNickname() throws Exception {

        // mock 객체
        CreateVideoRequest request = new CreateVideoRequest();
        request.setTitle("test");
        request.setDescription("test video");
        request.setNickname(null);
        request.setToken("token");
        request.setCategoryId(1);

        // 실행 결과 확인
        mockMvc.perform(post("/videos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createVideos_hasError_RequestNoToken() throws Exception {

        // mock 객체
        CreateVideoRequest request = new CreateVideoRequest();
        request.setTitle("test");
        request.setDescription("test video");
        request.setNickname("hongkildong");
        request.setToken(null);
        request.setCategoryId(1);

        // 실행 결과 확인
        mockMvc.perform(post("/videos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createVideos_hasError_RequestNoCategoryId() throws Exception {

        // mock 객체
        CreateVideoRequest request = new CreateVideoRequest();
        request.setTitle("test");
        request.setDescription("test video");
        request.setNickname("hongkildong");
        request.setToken("token");
        request.setCategoryId(null);

        // 실행 결과 확인
        mockMvc.perform(post("/videos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createVideos_hasError_NotFoundFile() throws Exception {

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
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createVideos_hasError_NotFoundUser() throws Exception {

        VideoFile 테스트_비디오 = VideoFile.builder()
                .path("test path")
                .build();

        videoFileRepository.save(테스트_비디오);

        // mock 객체
        CreateVideoRequest request = new CreateVideoRequest();
        request.setTitle("test");
        request.setDescription("test video");
        request.setNickname("no nickname");
        VideoFile videoFile = videoFileRepository.findByPath("test path");
        request.setToken(Base64Convertor.encode(videoFile.getId()));
        request.setCategoryId(1);

        // 실행 결과 확인
        mockMvc.perform(post("/videos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    void createVideos_hasError_InvalidCategoryId() throws Exception {

        User 홍길동 = User.builder()
                .nickname("hongkildong")
                .build();

        userRepository.save(홍길동);

        VideoFile 테스트_비디오 = VideoFile.builder()
                .path("test path")
                .build();

        videoFileRepository.save(테스트_비디오);

        // mock 객체
        CreateVideoRequest request = new CreateVideoRequest();
        request.setTitle("test");
        request.setDescription("test video");
        request.setNickname("hongkildong");
        VideoFile videoFile = videoFileRepository.findByPath("test path");
        request.setToken(Base64Convertor.encode(videoFile.getId()));
        request.setCategoryId(100);

        // 실행 결과 확인
        mockMvc.perform(post("/videos/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(csrf()))
                .andExpect(status().is4xxClientError());
    }
}