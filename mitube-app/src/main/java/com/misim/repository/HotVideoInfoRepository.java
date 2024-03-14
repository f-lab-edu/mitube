package com.misim.repository;

import com.misim.entity.HotVideoInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface HotVideoInfoRepository extends CrudRepository<HotVideoInfo, String> {

    @NonNull
    List<HotVideoInfo> findAll();
}
