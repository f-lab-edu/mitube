package com.misim.repository;

import com.misim.entity.WatchingInfo;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Repository
public class WatchingInfoRepository {

    private static final String REDIS_KEY = "watchingInfos";

    private final HashOperations<String, String, WatchingInfo> hashOperations;

    public WatchingInfoRepository(RedisTemplate<String, WatchingInfo> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(WatchingInfo watchingInfo) {
        hashOperations.put(REDIS_KEY, generateHashKey(watchingInfo), watchingInfo);
    }

    public Boolean existsByUserIdAndVideoId(Long userId, Long videoId) {
        return hashOperations.hasKey(REDIS_KEY, generateHashKey(userId, videoId));
    }

    public WatchingInfo findByUserIdAndVideoId(Long userId, Long videoId) {
        return hashOperations.get(REDIS_KEY, generateHashKey(userId, videoId));
    }

    public List<WatchingInfo> findLastTopTenByUserId(Long userId) {
        Map<String, WatchingInfo> watchingInfoMap = hashOperations.entries(REDIS_KEY);
        return watchingInfoMap.values().stream()
                .filter(info -> info.getUserId().equals(userId))
                .sorted(Comparator.comparing(WatchingInfo::getModifiedDate).reversed())
                .limit(10)
                .toList();
    }

    public void deleteById(Long userId, Long videoId) {
        hashOperations.delete(REDIS_KEY, generateHashKey(userId, videoId));
    }

    private String generateHashKey(WatchingInfo watchingInfo) {
        return generateHashKey(watchingInfo.getUserId(), watchingInfo.getVideoId());
    }

    private String generateHashKey(Long userId, Long videoId) {
        return userId + ":" + videoId;
    }

    public List<WatchingInfo> findLastTopHundred() {
        return hashOperations.entries(REDIS_KEY).values()
                .stream()
                .sorted(Comparator.comparing(WatchingInfo::getModifiedDate).reversed())
                .limit(100)
                .toList();
    }
}
