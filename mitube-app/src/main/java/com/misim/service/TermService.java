package com.misim.service;

import com.misim.controller.model.TermResponseDto;
import com.misim.entity.Term;
import com.misim.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TermService {

    private final TermRepository termRepository;


    public List<TermResponseDto> getAllTerms() {

        List<Term> terms = termRepository.findTermGroupByTitle();

        return terms.stream()
                .map(term -> TermResponseDto.builder()
                        .title(term.getTitle())
                        .isRequired(term.getIsRequired())
                        .build())
                .collect(Collectors.toList());
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