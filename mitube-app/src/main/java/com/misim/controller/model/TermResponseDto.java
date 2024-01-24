package com.misim.controller.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TermResponseDto {

    private final String title;

    private final String content;
}
