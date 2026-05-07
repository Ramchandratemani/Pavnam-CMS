package com.pavnam.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileStorageService {
    
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String storeFile(MultipartFile file, String subdirectory) throws IOException {
        // Create directories if they don't exist
        Path uploadPath = Paths.get(uploadDir + "/" + subdirectory);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate unique filename
        String originalFileName = file.getOriginalFilename();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + fileExtension;

        // Copy file to target location
        Path targetLocation = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return subdirectory + "/" + fileName;
    }

    public void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(uploadDir + "/" + filePath);
        Files.deleteIfExists(path);
    }

    
}