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

    @Builder
    public Video(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
}
