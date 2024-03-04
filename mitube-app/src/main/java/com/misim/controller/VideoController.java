package com.misim.controller;

import com.misim.controller.model.Response.UploadVideosResponse;
import com.misim.controller.model.VideoDto;
import com.misim.exception.CommonResponse;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.service.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "동영상 API", description = "동영상 정보 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/videos")
public class VideoController {

    private final VideoService videoService;
    
    @Operation(summary = "동영상 업로드", description = "새로운 동영상을 업로드합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "동영상 업로드 성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
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
    
    @Operation(summary = "동영상 생성", description = "새로운 동영상을 생성합니다.")
    @Parameter(name = "VideoDto", description = "Video 생성을 위한 데이터")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "동영상 생성 성공"),
            @ApiResponse(responseCode = "400", description = "요청 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @PostMapping("/create")
    public void createVideos(@RequestBody VideoDto videoDto) {

        // 파일 확인
        videoDto.check();
        
        // 비디오 생성
        videoService.createVideos(videoDto);
    }
    
    // get video - 비디오 호출 정보(유저, 호출 시간) 로그 저장
    // @GetMapping("/watch")
    // public void watchVideo(@)
}
