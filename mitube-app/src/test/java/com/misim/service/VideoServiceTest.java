package com.misim.service;

import com.misim.entity.VideoFile;
import com.misim.exception.MitubeException;
import com.misim.repository.VideoFileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @InjectMocks
    private VideoService videoService;

    @Mock
    private VideoFileRepository videoFileRepository;

    @Test
    @DisplayName("비디오파일 업로드 성공")
    void uploadVideos_Success() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile("file", "test-video.mp4", "video/mp4", "test video content".getBytes());

        when(videoFileRepository.save(any())).thenReturn(new VideoFile()); // Mocking save method to return a VideoFile

        // when
        String result = videoService.uploadVideos(file);

        // then
        verify(videoFileRepository, times(1)).save(any()); // Verify that save method is called once
        assertNotNull(result); // Assuming Base64Convertor.encode() doesn't return null
    }

    @Test
    @DisplayName("비디오파일 업로드 실패")
    void uploadVideos_Failure() throws Exception {
        // given
        MockMultipartFile file = new MockMultipartFile("file", "test-video.mp4", "video/mp4", "test video content".getBytes());

        when(videoFileRepository.save(any())).thenThrow(new RuntimeException("Simulated exception")); // Simulating an exception

        // when, then
        assertThrows(MitubeException.class, () -> videoService.uploadVideos(file));
    }

    @Test
    void createVideos() {
    }
}