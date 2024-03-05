package com.misim.entity;

import com.misim.service.VideoService;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "video_file")
@NoArgsConstructor
public class VideoFile extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String path;

    // 동영상 총 길이에 대한 필드 필요.

    @Builder
    public VideoFile(String path) {
        this.path = path;
    }
}
