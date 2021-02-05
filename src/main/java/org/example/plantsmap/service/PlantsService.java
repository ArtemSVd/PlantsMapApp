package org.example.plantsmap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.plantsmap.PlantRepository;
import org.example.plantsmap.dto.Plant;
import org.jooq.exception.IOException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class PlantsService {

    private final FileService fileService;

    private final PlantRepository plantRepository;

    public List<Integer> upload(List<Plant> plants, MultipartFile[] files) {
        // todo: сделать проверку юзера
        List<Integer> savedPlants = new ArrayList<>();

        for (MultipartFile file : files) {
            List<Plant> filteredPlants = plants
                    .stream()
                    .filter(p -> p.getFileName() != null && p.getFileName().equals(file.getOriginalFilename()))
                    .collect(Collectors.toList());

            if (!filteredPlants.isEmpty()) {
                try {
                    String filePath = fileService.upload(file);

                    Plant plant = filteredPlants.get(0);
                    plant.setFilePath(filePath);
                    plantRepository.create(plant);

                    savedPlants.add(plant.getId());
                } catch (IOException e) {
                    log.error("Ошибка при попытке сохранения файла:" + e.getMessage());
                } catch (Exception e) {
                    log.error("Ошибка при попытке сохранения сущности plant: " + e.getMessage());
                }
            }
        }

        return savedPlants;
    }

}
