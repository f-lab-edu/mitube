package com.misim.controller;

import com.misim.controller.model.TermResponseDto;
import com.misim.controller.model.VideoDto;
import com.misim.exception.CommonResponse;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/upload")
    public ResponseEntity<CommonResponse> uploadVideos(MultipartFile file) {

        // 파일 검사
        checkFile(file);

        // 비디오 업로드
        String id = videoService.uploadVideos(file);

        CommonResponse<String> commonResponse = new CommonResponse<>();
        commonResponse.setBody(id);

        return ResponseEntity.ok().body(commonResponse);
    }

    private void checkFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new MitubeException(MitubeErrorCode.EMPTY_FILE);
        }

        if (!file.getContentType().startsWith("video")) {
            throw new MitubeException(MitubeErrorCode.NOT_VIDEO_FILE);
        }
    }

    @PostMapping("/create")
    public void createVideos(VideoDto videoDto) {

        // 파일 확인
        videoDto.check();
        
        // 비디오 생성
        videoService.createVideos(videoDto);
    }
}
