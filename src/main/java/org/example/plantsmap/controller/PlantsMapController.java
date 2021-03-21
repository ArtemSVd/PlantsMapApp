package org.example.plantsmap.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.plantsmap.dto.Plant;
import org.example.plantsmap.dto.PlantsRequestParams;
import org.example.plantsmap.service.FileService;
import org.example.plantsmap.service.PlantsListService;
import org.example.plantsmap.service.PlantsService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "/plants", produces = "application/json; charset=UTF-8")
@AllArgsConstructor
@Log4j2
public class PlantsMapController {

    private final PlantsService service;
    private final FileService fileService;
    private final PlantsListService listService;

    @PostMapping(value = "/upload", consumes = {"multipart/form-data"})
    @ResponseBody
    public ResponseEntity<List<Integer>> upload(@RequestPart("json") List<Plant> plants, @RequestPart("image") MultipartFile[] image) {
        List<Integer> savedEntityIds = service.upload(plants, image);
        return ResponseEntity.ok(savedEntityIds);
    }

    @GetMapping(value = "/files/{fileName}")
    public ResponseEntity<Object> downloadFile(@PathVariable("fileName") String fileName) {
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

    @PostMapping("/list")
    public List<Plant> getList(@RequestBody PlantsRequestParams params) {
        return listService.list(params);
    }

    @GetMapping("/all")
    public List<Plant> listAll() {
        return listService.list(new PlantsRequestParams());
    }

    @GetMapping("/available")
    public boolean checkAvailable() {
        log.info("Чекают доступность");
        return true;
    }
}
