package com.misim.entity;

import com.misim.util.SecondaryIndexConvertor;
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
    private String key;

    private Long userId;

    private Long videoId;

    @Setter
    private Long watchingTime;

    @Builder
    public WatchingInfo(Long userId, Long videoId, Long watchingTime) {
        this.key = SecondaryIndexConvertor.encode(userId, videoId);
        this.userId = userId;
        this.videoId = videoId;
        this.watchingTime = watchingTime;
    }
}
