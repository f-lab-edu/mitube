package com.misim.controller;

import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;

    @PostMapping("/upload")
    public void uploadVideo(MultipartFile file) {
        if (file.isEmpty()) {
            throw new MitubeException(MitubeErrorCode.EMPTY_FILE);
        }
        videoService.uploadSingleVideo(file);
    }
}
