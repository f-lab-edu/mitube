package com.misim.controller.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@Schema(description = "전체 약관 제목 반환 DTO")
public class TermTitleResponseDto {

    @Schema(description = "약관 제목 리스트")
    private List<String> titles;
}
