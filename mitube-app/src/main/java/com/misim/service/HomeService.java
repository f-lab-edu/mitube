package com.misim.service;

import com.misim.controller.model.Response.HomeResponse;
import com.misim.entity.VideoCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final VideoService videoService;

    public HomeResponse getHome(Long userId) {

        return HomeResponse.builder()
                .categoryList(Arrays.stream(VideoCategory.values())
                        .map(VideoCategory::getName)
                        .toList())
                .hotVideoList(videoService.getHotVideos())
                .newVideoList(videoService.getNewVideos())
                .watchingVideoList(videoService.getWatchingVideos(userId))
                .subscribingChannelNewVideoList(videoService.getSubscribingChannelNewVideos(userId))
                .build();
    }
}
