package com.misim.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne
    @JoinColumn(name = "TERM_ID")
    private Term term;

    private boolean isAgree;

    @Builder
    public TermAgreement(Boolean isAgree, Term term) {
        this.isAgree = isAgree;
        this.term = term;
    }

    public void setUser(User user) {
        this.user = user;

        if (!user.getTermAgreements().contains(this)) {
            user.getTermAgreements().add(this);
        }
    }
}
