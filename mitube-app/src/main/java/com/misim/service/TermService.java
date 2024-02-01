package com.misim.service;

import com.misim.controller.model.TermResponseDto;
import com.misim.controller.model.TermTitleResponseDto;
import com.misim.entity.Term;
import com.misim.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TermService {

    private final TermRepository termRepository;


    public TermTitleResponseDto getTermTitles() {

        List<String> titleList = termRepository.findTitles();

        return TermTitleResponseDto.builder().titles(titleList).build();
    }

    public TermResponseDto getTermByTitle(String title) {

        Term term = termRepository.findTermByTitle(title);

        return TermResponseDto.builder()
                .title(term.getTitle())
                .content(term.getContent())
                .isRequired(term.getIsRequired())
                .build();
    }
}