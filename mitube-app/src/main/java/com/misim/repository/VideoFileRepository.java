package com.misim.repository;

import com.misim.entity.VideoFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoFileRepository extends JpaRepository<VideoFile, Long> {
    VideoFile findByPath(String path);
}
