package com.misim.controller;

import com.misim.controller.model.TermResponseDto;
import com.misim.service.TermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Terms API", description = "약관 내용을 제공하는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/terms/")
public class TermController {

    private final TermService termService;

    @Operation(summary = "약관", description = "약관 정보 가져오기")
    @GetMapping("/{termId}")
    public ResponseEntity<TermResponseDto> getTerm(@PathVariable Long termId) {

        TermResponseDto termResponseDto = termService.getTermById(termId);

        return ResponseEntity.ok().body(termResponseDto);
    }
}