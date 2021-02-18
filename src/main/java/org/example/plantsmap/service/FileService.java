package org.example.plantsmap.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.plantsmap.config.FileStorageConfig;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Log4j2
@Service
@AllArgsConstructor
public class FileService {

    private final FileStorageConfig config;

    public String upload(MultipartFile file) throws IOException {
        log.info("upload file: " + file.getOriginalFilename());
        String path = config.getLocation();

        File convertFile;
        convertFile = new File(path + file.getOriginalFilename());
        convertFile.createNewFile();

        try (FileOutputStream fout = new FileOutputStream(convertFile)) {
            fout.write(file.getBytes());
        }

        log.info("file uploaded successfully");
        return convertFile.getPath();
    }

    @Nullable
    public Resource getFileByName(String fileName) throws IOException {
        log.info("read file: " + fileName);
        Path path = Paths.get(config.getLocation() + fileName);
        Resource resource;

        resource = new UrlResource(path.toUri());
        if (!resource.getFile().exists()) {
            log.error("file not found: " + fileName);
            throw new FileNotFoundException("Файл не найден: " + fileName);
        }

        return resource;
    }
}
