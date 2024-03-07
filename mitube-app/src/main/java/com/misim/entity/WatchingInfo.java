package com.misim.entity;

import com.misim.util.TimeUtil;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "watching_infos")
@NoArgsConstructor
public class WatchingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long videoId;

    private LocalDateTime startTime;

    // 동영상을 시청한 시간에 대한 필드 필요.

    @Builder
    public WatchingInfo(Long userId, Long videoId) {
        this.userId = userId;
        this.videoId = videoId;
        this.startTime = TimeUtil.getNow();
    }
}
