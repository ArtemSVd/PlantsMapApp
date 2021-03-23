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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Service
@RequiredArgsConstructor
public class PlantsService {

    private final FileService fileService;

    private final PlantRepository plantRepository;

    private final UserContext userContext;

    private final PlantEntityTransformer transformer;

    public Map<Integer, Integer> upload(List<Plant> plants, MultipartFile[] files) {
        Map<Integer, Integer> processedPlants = new HashMap<>();

        for (Plant plant : plants) {
            MultipartFile file = Arrays.stream(files)
                    .filter(f -> f.getOriginalFilename() != null)
                    .filter(f -> plant.getFilePath() != null && plant.getFilePath().contains(f.getOriginalFilename()))
                    .findAny().orElse(null);

            plant.setUser(userContext.getUser());

            MPlant mPlant = plantRepository.getByIdFromDeviceAndUserId(plant.getId(), plant.getUser().getId());

            if (file != null && mPlant == null) {
                Integer id = savePlant(file, plant);
                if (id != null) {
                    processedPlants.put(plant.getId(), id);
                }
            } else if (mPlant != null) {
                Integer id = updatePlant(plant, mPlant);
                processedPlants.put(plant.getId(), id);
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

            return plantRepository.create(plant);

        } catch (IOException e) {
            log.error("Ошибка при попытке сохранения файла:" + e.getMessage());
            return null;
        }
    }

    private int updatePlant(Plant plant, MPlant mPlant) {
        log.info("Обновление сущности: " + plant);
        return plantRepository.update(plant, mPlant);
    }

    public Plant getById(Integer id) {
        MPlant mPlant = plantRepository.getById(id);
        return transformer.mapEntityToDto(mPlant);
    }

}
