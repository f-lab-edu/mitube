package com.misim.controller.model.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VideoResponse {

    private String title;

    private String description;

    private String category;

    private String nickname;

    private String videoUrl;

    private String thumbnailUrl;

    // 시청 중인 유저 정보 - 유저 닉네임과 동영상 시청 시각

    private Long views;
}
