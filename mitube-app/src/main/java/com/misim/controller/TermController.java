package com.misim.controller;

import com.misim.controller.model.TermResponseDto;
import com.misim.controller.model.TermTitleResponseDto;
import com.misim.service.TermService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Terms API", description = "약관 내용을 제공하는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/terms/")
public class TermController {

    private final TermService termService;

    // 모든 약관 타이틀 제시
    @Operation(summary = "약관", description = "전체 약관 타이틀 정보 가져오기")
    @GetMapping("/agree")
    public ResponseEntity<TermTitleResponseDto> getTermTitles() {

        TermTitleResponseDto titles = termService.getAllTermTitles();

        return ResponseEntity.ok().body(titles);
    }
    
    // 자세히 버튼 클릭 시 해당 약관에 대한 자세한 내용 제시
    @Operation(summary = "약관", description = "해당 약관 정책 정보 가져오기")
    @GetMapping("/policy")
    public ResponseEntity<TermResponseDto> getTermPolicy(@RequestParam Long termId) {

        TermResponseDto term = termService.getTermById(termId);

        return ResponseEntity.ok().body(term);
    }
}