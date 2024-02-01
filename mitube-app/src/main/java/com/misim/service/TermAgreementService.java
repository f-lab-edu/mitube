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

import java.util.ArrayList;
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
