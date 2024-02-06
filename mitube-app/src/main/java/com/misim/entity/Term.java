package com.misim.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "terms")
@NoArgsConstructor
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Boolean isRequired;

    @OneToMany(mappedBy = "term")
    private List<TermAgreement> termAgreements = new ArrayList<TermAgreement>();

    @Builder
    public Term(String title, String content, Boolean isRequired) {
        this.title = title;
        this.content = content;
        this.isRequired = isRequired;
    }

    public void addTermAgreements(TermAgreement termAgreement) {
        this.termAgreements.add(termAgreement);

        if (termAgreement.getTerm() != this) {
            termAgreement.setTerm(this);
        }
    }
}
