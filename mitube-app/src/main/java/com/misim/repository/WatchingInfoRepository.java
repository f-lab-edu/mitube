package com.misim.repository;

import com.misim.entity.WatchingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface WatchingInfoRepository extends JpaRepository<WatchingInfo, Long> {

    @Query("SELECT w.videoId, COUNT(w.videoId) as viewCount FROM WatchingInfo w WHERE w.startTime >= :thirtyMinutesAgo GROUP BY w.videoId ORDER BY viewCount DESC LIMIT 10")
    List<WatchingInfo> findHotWatchingInfo(LocalDateTime thirtyMinutesAge);

    @Query("SELECT w FROM WatchingInfo w WHERE w.userId = :userId")
    List<WatchingInfo> findWatchingInfoByUserId(Long userId);
}
