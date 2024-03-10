package com.misim.repository;

import com.misim.entity.User;
import com.misim.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("SELECT v FROM Video v WHERE v.user.nickname IN :userIds GROUP BY v.user ORDER BY MAX(v.createdDate) DESC")
    List<Video> findTopByUserId(List<Long> userIds);
}
