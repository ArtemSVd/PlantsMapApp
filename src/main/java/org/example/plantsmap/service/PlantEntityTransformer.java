package org.example.plantsmap.service;

import lombok.AllArgsConstructor;
import org.example.plantsmap.dto.Coordinate;
import org.example.plantsmap.dto.Plant;
import org.example.plantsmap.enums.KingdomType;
import org.example.plantsmap.generated.tables.pojos.MPlant;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlantEntityTransformer {
    private final UserService userService;

    public Plant mapEntityToDto(MPlant mPlant) {
        return Plant.builder()
                .id(mPlant.getId())
                .name(mPlant.getName())
                .coordinate(Coordinate.builder()
                        .latitude(mPlant.getLatitude())
                        .longitude(mPlant.getLongitude())
                        .build())
                .description(mPlant.getDescription())
                .filePath(mPlant.getFilePath())
                .type(KingdomType.valueOf(mPlant.getKingdomType()))
                .user(userService.getById(mPlant.getUserId()))
                .build();
    }

}
