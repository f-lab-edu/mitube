package com.misim.repository;

import com.misim.entity.WatchingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WatchingInfoRepository extends JpaRepository<WatchingInfo, Long> {

    @Query("SELECT videoId, COUNT(videoId) as viewCount FROM WatchingInfo WHERE startTime >= (NOW() - INTERVAL 30 MINUTE) GROUP BY videoId ORDER BY viewCount DESC LIMIT 10")
    List<WatchingInfo> findHotWatchingInfo();

    @Query("select w1 from WatchingInfo w1 where w1.userId = :userId LIMIT 10")
    List<WatchingInfo> findWatchingInfoByUserId(Long userId);
}
