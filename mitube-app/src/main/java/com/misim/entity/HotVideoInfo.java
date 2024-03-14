package com.misim.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@RedisHash(value = "hotVideoInfo", timeToLive = 1800)
public class HotVideoInfo implements Serializable {

    @Id
    private String id;

    private Long videoId;

    @Builder
    public HotVideoInfo(Long videoId) {
        this.videoId = videoId;
    }
}
