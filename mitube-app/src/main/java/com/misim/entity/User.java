package com.misim.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "users", indexes = {@Index(name = "idx_email", columnList = "email", unique = true), @Index(name = "idx_nickname", columnList = "nickname", unique = true)})
@NoArgsConstructor
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 32)
    private String email;

    @Column(length = 15)
    private String password;

    @Column(length = 20)
    private String nickname;

    @Builder
    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
