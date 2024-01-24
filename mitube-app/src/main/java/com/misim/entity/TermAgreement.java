package com.misim.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "term_agreements")
@NoArgsConstructor
public class TermAgreement extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "TERM_ID")
    private Term term;

    private boolean isAgree;

    @Builder
    public TermAgreement(User user, Term term, Boolean isAgree) {
        this.user = user;
        this.term = term;
        this.isAgree = isAgree;
    }
}
