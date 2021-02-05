package org.example.plantsmap.service;

import lombok.AllArgsConstructor;
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


@Service
@AllArgsConstructor
public class FileService {

    private final FileStorageConfig config;

    public String upload(MultipartFile file) throws IOException {
        String path = config.getLocation();

        File convertFile;
        convertFile = new File(path + file.getOriginalFilename());
        convertFile.createNewFile();

        try (FileOutputStream fout = new FileOutputStream(convertFile)) {
            fout.write(file.getBytes());
        }

        return convertFile.getPath();
    }

    @Nullable
    public Resource getFileByName(String fileName) throws IOException {
        Path path = Paths.get(config.getLocation() + fileName);
        Resource resource;

        resource = new UrlResource(path.toUri());
        if (!resource.getFile().exists()) {
            throw new FileNotFoundException("Файл не найден: " + fileName);
        }

        return resource;
    }
}
