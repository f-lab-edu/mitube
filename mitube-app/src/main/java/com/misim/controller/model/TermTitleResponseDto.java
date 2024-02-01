package com.misim.controller.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class TermTitleResponseDto {

    private List<String> titles;
}
