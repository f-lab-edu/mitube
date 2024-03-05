package com.misim.controller.model.Response;

import lombok.Data;

import java.util.List;

@Data
public class HomeResponse {

    private List<String> categoryList;

    private List<VideoResponse> newVideoList;

    private List<VideoResponse> hotVideoList;

    private List<VideoResponse> watchingVideoList;

    private List<VideoResponse> subscribingChannelNewVideoList;
}
