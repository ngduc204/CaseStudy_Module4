package com.star_movies.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class HandleUploadService {

    @Value("${app.images.upload-root}")
    private String uploadRootPath;

    public String handleUpload(MultipartFile file, String uploadDir) throws IOException {
        Path uploadPath = Paths.get(uploadRootPath + uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();

        String fileExtension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            fileExtension = originalFilename.substring(dotIndex);
        }

        String newFilename = UUID.randomUUID().toString() + fileExtension;

        File destFile = new File(uploadPath.toString(), newFilename);

        try {
            file.transferTo(destFile);
        } catch (IOException e) {
            throw new IOException("Failed to upload file: " + e.getMessage());
        }
        return "/images/" + uploadDir + "/" + newFilename;
    }
}