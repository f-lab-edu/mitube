package com.misim.repository;

import com.misim.entity.WatchingInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchingInfoRepository extends CrudRepository<WatchingInfo, String> {

    List<WatchingInfo> findAllByUserId(Long userId);

    Boolean existsByKey(String key);

    WatchingInfo findByKey(String key);
}
