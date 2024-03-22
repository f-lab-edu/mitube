package com.misim.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@RedisHash(value = "watchingInfo")
public class WatchingInfo implements Serializable {

    @Id
    private String id;

    @Indexed
    private Long userId;

    private Long videoId;

    @Setter
    private Long watchingTime;

    @Builder
    public WatchingInfo(Long userId, Long videoId, Long watchingTime) {
        this.userId = userId;
        this.videoId = videoId;
        this.watchingTime = watchingTime;
    }
}
