package com.misim.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private boolean isMandatory;

    @Builder
    public Term(String title, String content, Boolean isMandatory) {
        this.title = title;
        this.content = content;
        this.isMandatory = isMandatory;
    }
}
