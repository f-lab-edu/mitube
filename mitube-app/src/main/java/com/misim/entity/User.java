package com.misim.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "users", indexes = {@Index(name = "idx_email", columnList = "email", unique = true), @Index(name = "idx_nickname", columnList = "nickname", unique = true), @Index(name = "idx_phoneNb", columnList = "phoneNumber", unique = true)})
@NoArgsConstructor
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32)
    private String email;

    @Column(length = 60)
    private String password;

    @Column(length = 20)
    private String nickname;

    private String phoneNumber;

    private boolean isEnabled;

    @OneToMany(mappedBy = "user")
    private List<TermAgreement> termAgreements = new ArrayList<TermAgreement>();

    @Builder
    public User(String email, String password, String nickname, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.isEnabled = true;
    }

    public void addTermAgreements(TermAgreement termAgreement) {
        this.termAgreements.add(termAgreement);

        if (termAgreement.getUser() != this) {
            termAgreement.setUser(this);
        }
    }
}
