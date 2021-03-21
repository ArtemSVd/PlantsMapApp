package org.example.plantsmap.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.plantsmap.generated.tables.pojos.MPlant;
import org.example.plantsmap.repository.PlantRepository;
import org.example.plantsmap.dto.Plant;
import org.example.plantsmap.security.UserContext;
import java.io.IOException;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class PlantsService {

    private final FileService fileService;

    private final PlantRepository plantRepository;

    private final UserContext userContext;

    public List<Integer> upload(List<Plant> plants, MultipartFile[] files) {
        List<Integer> processedPlants = new ArrayList<>();

        for (Plant plant : plants) {
            MultipartFile file = Arrays.stream(files)
                    .filter(f -> f.getOriginalFilename() != null)
                    .filter(f -> plant.getFilePath() != null && plant.getFilePath().contains(f.getOriginalFilename()))
                    .findAny().orElse(null);

            plant.setUser(userContext.getUser());

            MPlant mPlant = plantRepository.getByIdFromDeviceAndUserId(plant.getId(), plant.getUser().getId());

            if (file != null && mPlant == null) {
                processedPlants.add(savePlant(file, plant));
            } else if (mPlant != null) {
                processedPlants.add(updatePlant(plant, mPlant));
            }
        }

        return processedPlants;
    }

    @Nullable
    private Integer savePlant(MultipartFile file, Plant plant) {
        try {
            String filePath = fileService.upload(file);

            plant.setFilePath(filePath);

            log.info("Сохранение сущности: " + plant);

            plantRepository.create(plant);

            return plant.getId();
        } catch (IOException e) {
            log.error("Ошибка при попытке сохранения файла:" + e.getMessage());
            return null;
        }
    }

    private Integer updatePlant(Plant plant, MPlant mPlant) {
        log.info("Обновление сущности: " + plant);
        plantRepository.update(plant, mPlant);
        return plant.getId();
    }


}
