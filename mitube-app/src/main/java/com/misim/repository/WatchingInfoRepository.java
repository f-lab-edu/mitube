package com.misim.repository;

import com.misim.entity.WatchingInfo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WatchingInfoRepository extends CrudRepository<WatchingInfo, Long> {

    List<WatchingInfo> findAllByUserId(Long userId);

    Boolean existsByUserIdAndVideoId(Long userId, Long videoId);

    WatchingInfo findByUserIdAndVideoId(Long userId, Long videoId);
}
