package com.misim.service;

import com.misim.controller.model.Request.CreateVideoRequest;
import com.misim.controller.model.Response.VideoResponse;
import com.misim.entity.User;
import com.misim.entity.Video;
import com.misim.entity.VideoCategory;
import com.misim.entity.VideoFile;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.UserRepository;
import com.misim.repository.VideoFileRepository;
import com.misim.repository.VideoRepository;
import com.misim.util.Base64Convertor;
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

        String folderStr = UPLOAD_PATH + File.separator + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));

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
        return File.separator + UUID.randomUUID().toString() + originalFilename;
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

    public List<VideoResponse> getNewVideos() {
        return new ArrayList<>();
    }

    public List<VideoResponse> getHotVideos() {
        return new ArrayList<>();
    }
}
