package com.misim.service;


import com.misim.entity.Term;
import com.misim.entity.TermAgreement;
import com.misim.entity.User;
import com.misim.exception.MitubeErrorCode;
import com.misim.exception.MitubeException;
import com.misim.repository.TermAgreementRepository;
import com.misim.repository.TermRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TermAgreementService {

    private final TermAgreementRepository termAgreementRepository;
    private final TermRepository termRepository;

    protected void setTermAgreements(User user, List<Boolean> agreements) {
        List<Term> termList = termRepository.findAll(Sort.by("id"));

        if (agreements.size() != termList.size()) {
            throw new MitubeException(MitubeErrorCode.CHECK_TERMS_UPDATE);
        }

        setTermAgreement(agreements, termList, user);
    }

    private void setTermAgreement(List<Boolean> agreements, List<Term> terms, User user) {
        TermAgreement termAgreement;
        for (int i=0; i<agreements.size(); i++) {
            termAgreement = TermAgreement.builder()
                    .user(user)
                    .term(terms.get(i))
                    .isAgree(agreements.get(i))
                    .build();

            termAgreementRepository.save(termAgreement);
        }
    }
}
