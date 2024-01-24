package com.misim.controller.model;

import com.misim.entity.Term;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
public class TermResponseDto {

    private final String title;

    private final String content;
}
