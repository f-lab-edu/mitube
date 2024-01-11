package com.misim.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String email;

    private String password;

    private String nickname;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @Builder
    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.createDate = LocalDateTime.now();
        this.modifyDate = LocalDateTime.now();
    }
}
