package com.misim.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "videos")
@NoArgsConstructor
public class Video extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ManyToOne
    @Setter
    private User user;

    @ManyToOne
    @Setter
    private VideoFile videoFile;

    private Integer categoryId;

    // 연관관계 설정 가능
    private Long views;

    // 연관관계 설정 가능
    private String thumbnailUrl;

    @Builder
    public Video(String title, String description, Integer categoryId, Long views, String thumbnailUrl) {
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.views = views;
        this.thumbnailUrl = thumbnailUrl;
    }
}
