package com.misim.controller;

import com.misim.controller.model.Response.UploadVideosResponse;
import com.misim.controller.model.VideoDto;
import com.misim.exception.CommonResponse;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/upload")
    public CommonResponse<UploadVideosResponse> uploadVideos(@RequestParam MultipartFile file) {

        // 파일 검사
        checkFile(file);

        // 비디오 업로드
        String id = videoService.uploadVideos(file);

        UploadVideosResponse uploadVideosResponse = new UploadVideosResponse();
        uploadVideosResponse.setId(id);

        return CommonResponse
                .<UploadVideosResponse>builder()
                .body(uploadVideosResponse)
                .build();
    }

    private void checkFile(MultipartFile file) {

        if (file == null || file.isEmpty()) {
            throw new MitubeException(MitubeErrorCode.EMPTY_FILE);
        }
    }

    @PostMapping("/create")
    public void createVideos(@RequestBody VideoDto videoDto) {

        // 파일 확인
        videoDto.check();
        
        // 비디오 생성
        videoService.createVideos(videoDto);
    }
}
