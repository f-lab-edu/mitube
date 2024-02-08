package com.misim.service;


import com.misim.entity.Term;
import com.misim.entity.TermAgreement;
import com.misim.entity.User;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.TermAgreementRepository;
import com.misim.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TermAgreementService {

    private final TermAgreementRepository termAgreementRepository;
    private final TermRepository termRepository;

    protected void associateTermAgreements(User user, List<Boolean> agreements) {

        // Term api에서 전체 약관 정보 전달 시 사용하는 레포지토리 메소드가 아래의 메소드와 같아서 동일한 순서임이 보장된다.
        // 인자로 받은 agreements와 terms의 순서가 같다.
        List<Term> terms = termRepository.findTermGroupByTitle();

        if (agreements.size() != terms.size()) {
            throw new MitubeException(MitubeErrorCode.CHECK_TERMS_UPDATE);
        }

        saveTermAgreements(agreements, terms, user);
    }

    private void saveTermAgreements(List<Boolean> agreements, List<Term> terms, User user) {

        List<TermAgreement> termAgreements = new ArrayList<>();

        for (int i=0; i<agreements.size(); i++) {
            TermAgreement termAgreement = TermAgreement.builder()
                    .isAgree(agreements.get(i))
                    .build();

            termAgreement.setTerm(terms.get(i));
            termAgreement.setUser(user);

            termAgreements.add(termAgreement);
        }

        termAgreementRepository.saveAll(termAgreements);
    }
}
