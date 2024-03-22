package com.misim.repository;

import com.misim.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("SELECT v FROM Video v WHERE v.user.id IN :userIds GROUP BY v.user ORDER BY MAX(v.createdDate) DESC")
    List<Video> findLastByUserId(List<Long> userIds);

    @Query(value = "SELECT v FROM Video v OREDER BY v.modifiedDate LIMIT 10", nativeQuery = true)
    List<Video> findTopTen();

    Video findByTitle(String title);
}
