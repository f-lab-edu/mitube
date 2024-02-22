package com.misim.controller.model.Response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TermDetailResponse extends TermResponse{

    @Schema(description = "약관 내용")
    private String content;

    public TermDetailResponse(String title, Boolean isRequired) {
        super(title, isRequired);
    }
}
