package com.misim.controller.model.Response;

import com.misim.entity.Video;
import com.misim.entity.VideoCategory;
import lombok.Builder;
import lombok.Data;

import java.util.List;

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

    public static List<VideoResponse> convertVideos(List<Video> videos) {
        return videos.stream()
            .map(video -> VideoResponse.builder()
                    .title(video.getTitle())
                    .description(video.getDescription())
                    .nickname(video.getUser().getNickname())
                    .category(VideoCategory.getNameByCode(video.getCategoryId()))
                    .videoUrl(video.getVideoFile().getPath())
                    .views(video.getViews())
                    .thumbnailUrl(video.getThumbnailUrl())
                    .build())
            .toList();
    }
}
