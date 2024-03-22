package com.misim.service;

import com.misim.controller.model.Request.CreateVideoRequest;
import com.misim.controller.model.Response.StartWatchingVideoResponse;
import com.misim.controller.model.Response.VideoResponse;
import com.misim.entity.*;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.*;
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
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoService {

    @Value("upload.path")
    private String UPLOAD_PATH;

    private final VideoRepository videoRepository;
    private final VideoFileRepository videoFileRepository;
    private final UserRepository userRepository;
    private final WatchingInfoRepository watchingInfoRepository;
    private final SubscriptionRepository subscriptionRepository;

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

    public StartWatchingVideoResponse startWatchingVideo (Long videoId, Long userId) {

        if (!videoRepository.existsById(videoId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_VIDEO);
        }

        if (watchingInfoRepository.existsByUserIdAndVideoId(userId, videoId)) {

            WatchingInfo watchingInfo = watchingInfoRepository.findByUserIdAndVideoId(userId, videoId);

            if (watchingInfo == null) {
                throw new MitubeException(MitubeErrorCode.NOT_FOUND_WATCHING_INFO);
            }

            return StartWatchingVideoResponse.builder()
                    .watchingTime(watchingInfo.getWatchingTime())
                    .build();

        } else {

            WatchingInfo watchingInfo = WatchingInfo.builder()
                    .videoId(videoId)
                    .userId(userId)
                    .watchingTime(0L)
                    .build();

            watchingInfoRepository.save(watchingInfo);

            return StartWatchingVideoResponse.builder()
                    .watchingTime(watchingInfo.getWatchingTime())
                    .build();
        }
    }

    public void updateWatchingVideoInfo(Long videoId, Long userId, Long watchingTime) {

        if (!watchingInfoRepository.existsByUserIdAndVideoId(userId, videoId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_WATCHING_INFO);
        }

        WatchingInfo watchingInfo = watchingInfoRepository.findByUserIdAndVideoId(userId, videoId);

        if (watchingInfo == null) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_WATCHING_INFO);
        }

        watchingInfo.setWatchingTime(watchingTime);
        watchingInfo.setModifiedDate(TimeUtil.getNow());

        watchingInfoRepository.save(watchingInfo);
    }

    public List<VideoResponse> getNewVideos() {

        List<Video> videos = videoRepository.findTopTen();

        return VideoResponse.convertVideos(videos);
    }

    public List<VideoResponse> getHotVideos() {

        // redis template, sorted hash
        // 가장 최근 시청 정보 중 100개를 가져와서 인기도 측정

        List<WatchingInfo> watchingInfos = watchingInfoRepository.findLastTopHundred();

        List<Long> top10VideoIds = watchingInfos.stream()
                .collect(Collectors.groupingBy(WatchingInfo::getVideoId, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .toList();

        List<Video> videos = videoRepository.findAllById(top10VideoIds);

        return VideoResponse.convertVideos(videos);
    }

    public List<VideoResponse> getWatchingVideos(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        List<WatchingInfo> watchingInfos = watchingInfoRepository.findLastTopTenByUserId(userId);

        List<Video> videos = videoRepository.findAllById(
                watchingInfos.stream()
                .map(WatchingInfo::getVideoId)
                .toList()
        );

        return VideoResponse.convertVideos(videos);
    }

    public List<VideoResponse> getSubscribingChannelNewVideos(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new MitubeException(MitubeErrorCode.NOT_FOUND_USER);
        }

        List<Subscription> subscriptions = subscriptionRepository.findSubscriptionsBySubscriberId(userId);

        List<Video> videos = videoRepository.findLastByUserId(
                subscriptions.stream()
                .map(Subscription::getOwnerId)
                .limit(10)
                .toList()
        );

        return VideoResponse.convertVideos(videos);
    }
}
