package com.misim.controller.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TermResponseDto {

    private String title;

    private String content;
}