package com.misim.service;

import com.misim.controller.model.Request.CreateVideoRequest;
import com.misim.controller.model.Response.VideoResponse;
import com.misim.entity.*;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.UserRepository;
import com.misim.repository.VideoFileRepository;
import com.misim.repository.VideoRepository;
import com.misim.repository.WatchingInfoRepository;
import com.misim.util.Base64Convertor;
import com.misim.util.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoService {

    @Value("upload.path")
    private String UPLOAD_PATH;

    private final VideoRepository videoRepository;
    private final VideoFileRepository videoFileRepository;
    private final UserRepository userRepository;
    private final WatchingInfoRepository watchingInfoRepository;

    public String uploadVideos(MultipartFile file) {

        String uploadDir = makeFolder();

        String saveFileName = createFileName(file.getOriginalFilename());

        String saveFile = uploadDir + File.separator + saveFileName;

        Path saveFilePath = Paths.get(saveFile);

        try {
            file.transferTo(saveFilePath);
        } catch (IOException e) {
            e.fillInStackTrace();
            throw new MitubeException(MitubeErrorCode.NOT_CREATED_FILE);
        }

        VideoFile videoFile = videoFileRepository.save(VideoFile.builder().path(saveFile).build());

        return Base64Convertor.encode(videoFile.getId());
    }

    private String makeFolder() {

        String folderStr = UPLOAD_PATH + File.separator + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy_MM_dd"));

        Path folder = Paths.get(folderStr);

        if (!Files.exists(folder)) {
            try {
                Files.createDirectory(folder);
            } catch (IOException e) {
                throw new MitubeException(MitubeErrorCode.NOT_CREATED_DIR);
            }
        }

        return folderStr;
    }

    private String createFileName(String originalFilename) {
        return UUID.randomUUID() + "_" + originalFilename;
    }

    public void createVideos(CreateVideoRequest createVideoRequest) {

        Long videoFileId = Base64Convertor.decode(createVideoRequest.getToken());

        // 비디오 파일 확인
        if (!videoFileRepository.existsById(videoFileId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO_FILE);
        }

        // 유저 확인
        if (!userRepository.existsByNickname(createVideoRequest.getNickname())) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        // 비디오 카테고리 확인
        if (!VideoCategory.existByCode(createVideoRequest.getCategoryId())) {
            throw new MitubeException(MitubeErrorCode.INVALID_CATEGORY);
        }

        Video video = Video.builder()
                .title(createVideoRequest.getTitle())
                .description(createVideoRequest.getDescription())
                .categoryId(createVideoRequest.getCategoryId())
                .views(0L)
                .thumbnailUrl("")
                .build();

        // 비디오 파일 연결
        VideoFile videoFile =  videoFileRepository.findById(videoFileId)
                .orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO_FILE));

        video.setVideoFile(videoFile);

        // 유저 연결
        User user = userRepository.findByNickname(createVideoRequest.getNickname());

        video.setUser(user);

        // 비디오 저장
        videoRepository.save(video);
    }

    public void watchVideos(Long videoId, Long userId) {

        if (!videoRepository.existsById(videoId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO);
        }

        WatchingInfo watchingInfo = WatchingInfo.builder()
                .videoId(videoId)
                .userId(userId)
                .build();

        watchingInfoRepository.save(watchingInfo);
    }

    public List<VideoResponse> getNewVideos() {
        List<Video> videos = videoRepository.findAll();

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

    public List<VideoResponse> getHotVideos() {

        LocalDateTime current = TimeUtil.getNow().minusMinutes(30);

        List<WatchingInfo> watchingInfos = watchingInfoRepository.findHotWatchingInfo(current);

        return getVideoResponseByWatchingInfo(watchingInfos);
    }

    public List<VideoResponse> getWatchingVideos(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        List<WatchingInfo> watchingInfos = watchingInfoRepository.findWatchingInfoByUserId(userId);

        return getVideoResponseByWatchingInfo(watchingInfos);
    }

    // channel 엔티티 생성 및 유저 구독 연관관계 설정 필요
    public List<VideoResponse> getSubscribingChannelNewVideos(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        // List<WatchingInfo> watchingInfos = watchingInfoRepository.();

        return new ArrayList<VideoResponse>();
    }

    List<VideoResponse> getVideoResponseByWatchingInfo(List<WatchingInfo> watchingInfos) {

        List<Video> videos = videoRepository.findAllById(watchingInfos.stream()
                .map(WatchingInfo::getVideoId)
                .toList());

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
