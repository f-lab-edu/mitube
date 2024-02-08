package com.misim.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class VideoService {

    private static String UPLOAD_DIR = "uploads/";

    public void uploadSingleVideo(MultipartFile file) {
        try {
            Path uploadDir = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            Path filePath = uploadDir.resolve(file.getName());
            Files.copy(file.getInputStream(), filePath);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
