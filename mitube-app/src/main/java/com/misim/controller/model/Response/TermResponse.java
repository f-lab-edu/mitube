package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "약관 정보 반환 DTO")
public class TermResponse {

    @Schema(description = "약관 제목")
    private String title;

    @Schema(description = "약관 타입")
    private Boolean isRequired;

    @Builder
    public TermResponse(String title, Boolean isRequired) {
        this.title = title;
        this.isRequired = isRequired;
    }
}
