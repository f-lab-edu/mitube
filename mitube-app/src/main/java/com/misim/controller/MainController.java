package com.misim.controller;

import com.misim.controller.model.Response.HomeResponse;
import com.misim.entity.VideoCategory;
import com.misim.exception.CommonResponse;
import com.misim.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final VideoService videoService;

    @GetMapping("/home")
    public CommonResponse<HomeResponse> home(@RequestParam Long userId) {

        HomeResponse response = new HomeResponse();

        // 카테고리 정보
        response.setCategoryList(Arrays.stream(VideoCategory.values())
                .map(VideoCategory::getName)
                .toList());

        // 최신 동영상
        response.setNewVideoList(videoService.getNewVideos());

        // 인기 동영상
        response.setHotVideoList(videoService.getHotVideos());

        // 내가 시청 중인 동영상
        response.setWatchingVideoList(videoService.getWatchingVideos(userId));

        // 구독 채널의 새 동영상
        response.setSubscribingChannelNewVideoList(videoService.getSubscribingChannelNewVideos(userId));

        return CommonResponse
                .<HomeResponse>builder()
                .body(response)
                .build();
    }
}
