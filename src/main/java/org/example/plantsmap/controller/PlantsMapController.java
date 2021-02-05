package org.example.plantsmap.controller;

import lombok.AllArgsConstructor;
import org.example.plantsmap.dto.Plant;
import org.example.plantsmap.service.FileService;
import org.example.plantsmap.service.PlantsService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/plants", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
public class PlantsMapController {

    private final PlantsService service;
    private final FileService fileService;

    // todo: подумать над ответом
    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    @ResponseBody
    public ResponseEntity<List<Integer>> upload(@CookieValue("user") String cookie, @RequestPart("json") List<Plant> plants, @RequestPart("image") MultipartFile[] image) {
        //Пока возвращаем список сохраненных
        List<Integer> savedEntityIds = service.upload(plants, image);
        return ResponseEntity.ok(savedEntityIds);
    }

    @GetMapping(value = "/file/{fileName}")
    public ResponseEntity<Object> downloadFile( @PathVariable("fileName") String fileName) {
        try {
            Resource resource = fileService.getFileByName(fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    //todo: Сделать апи для получения элементов с учетом фильтров
    @PostMapping("/list")
    public List<Plant> getList(@CookieValue("user") String cookie) {
        List<Plant> list = new ArrayList<>();
        return list;
    }

}
