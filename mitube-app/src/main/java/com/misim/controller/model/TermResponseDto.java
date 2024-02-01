package com.misim.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "상세 약관 정보 반환 DTO")
public class TermResponseDto {

    @Schema(description = "약관 제목")
    private String title;

    @Schema(description = "약관 내용")
    private String content;
    
    @Schema(description = "약관 타입")
    private Boolean isRequired;
}
