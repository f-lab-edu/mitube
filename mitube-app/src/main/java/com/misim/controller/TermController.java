package com.misim.controller;

import com.misim.controller.model.Response.TermDetailResponse;
import com.misim.controller.model.Response.TermListResponse;
import com.misim.controller.model.Response.TermResponse;
import com.misim.exception.CommonResponse;
import com.misim.service.TermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "약관 API", description = "약관 정보 제공 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/terms/")
public class TermController {

    private final TermService termService;

    // 모든 약관 타이틀 제시
    @Operation(summary = "전체 약관 전송", description = "전체 약관 정보 가져오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "전체 약관 정보 전달 성공.")})
    @GetMapping("/agree")
    public CommonResponse<TermListResponse> getTerms() {

        TermListResponse terms = termService.getAllTerms();

        return CommonResponse
                .<TermListResponse>builder()
                .body(terms)
                .build();
    }
    
    // 자세히 버튼 클릭 시 해당 약관에 대한 자세한 내용 제시
    @Operation(summary = "약관 상세 정보 전송", description = "해당 약관 정책 정보 가져오기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "제목으로 약관 조회 성공."),
            @ApiResponse(responseCode = "400", description = "제목 형식이 올바르지 않습니다.", content = @Content(schema = @Schema(implementation = CommonResponse.class)))
    })
    @GetMapping("/policy")
    public CommonResponse<TermResponse> getTermPolicy(@RequestParam @Parameter(description = "약관 제목", in = ParameterIn.QUERY, example = "개인 정보 보호") String title) {

        TermDetailResponse response = termService.getTermByTitle(title);

        return CommonResponse
                .<TermResponse>builder()
                .body(response)
                .build();
    }
}