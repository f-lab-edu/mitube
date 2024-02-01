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
public class Term extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private Boolean isRequired;
    
    // 버전 -> 최신 버전
    
    // 그룹 -> 그룹바이

    // OneToMany user, termAgreement
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
