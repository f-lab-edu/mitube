package com.misim.service;

import com.misim.controller.model.TermResponseDto;
import com.misim.controller.model.TermTitleResponseDto;
import com.misim.entity.Term;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TermService {

    private final TermRepository termRepository;


    public TermTitleResponseDto getAllTermTitles() {
        List<String> titleList = termRepository.findAllTitle();

        return TermTitleResponseDto.builder().titles(titleList).build();
    }

    public TermResponseDto getTermById(Long id) {
        Term term = termRepository.findById(id).orElseThrow(() -> new MitubeException(MitubeErrorCode.NOT_FOUND_TERM));

        return TermResponseDto.builder()
                .title(term.getTitle())
                .content(term.getContent())
                .build();
    }
}