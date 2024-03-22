package com.misim.repository;

import com.misim.entity.WatchingInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchingInfoRepository extends CrudRepository<WatchingInfo, String> {

    Boolean existsByUserId(Long userId);

    List<WatchingInfo> findAllByUserId(Long userId);

    @NonNull
    List<WatchingInfo> findAll();
}
