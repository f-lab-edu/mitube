package com.misim.service;

import com.misim.controller.model.Response.TermDetailResponse;
import com.misim.controller.model.Response.TermListResponse;
import com.misim.controller.model.Response.TermResponse;
import com.misim.entity.Term;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TermService {

    private final TermRepository termRepository;


    public TermListResponse getAllTerms() {

        List<Term> terms = termRepository.findTermGroupByTermGroupAndMaxVersion();

        List <TermResponse> termResponses = terms.stream()
                .map(term -> TermResponse.builder()
                        .title(term.getTitle())
                        .isRequired(term.getIsRequired())
                        .build())
                .toList();

        return TermListResponse
                .builder()
                .termResponseList(termResponses)
                .build();
    }

    public TermDetailResponse getTermByTitle(String title) {

        Term term = termRepository.findTermByTitleAndMaxVersion(title);

        TermDetailResponse response = new TermDetailResponse(term.getTitle(), term.getIsRequired());
        response.setContent(term.getContent());

        return response;
    }

    public void checkTerms(List<String> checkedTermTitles) {

        List<Term> terms = termRepository.findTermGroupByTermGroupAndMaxVersion();
        
        int cnt = 0;
        
        // db에서 조회한 약관 중 필수 약관이 유저가 동의한 약관 제목에 존재하는지 확인
        // 한 번의 for 반복문에서 cnt도 처리하기 위해 포함관계를 확인한 후 필수 약관인지 확인한다.
        for (Term t : terms) {
            if (checkedTermTitles.contains(t.getTitle())) {
                cnt++;
            } else {
                if (t.getIsRequired()) {
                    throw new MitubeException(MitubeErrorCode.NOT_AGREE_REQUIRED_TERM);
                }
            }
        }

        // 유저가 동의한 약관 제목이 db에서 조회한 약관 목록에 모두 포함되는지 확인
        if (checkedTermTitles.size() != cnt) {
            throw new MitubeException(MitubeErrorCode.NOT_MATCH_TERM_AND_TERM_AGREEMENT);
        }
    }
}