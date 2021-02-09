package org.example.plantsmap.service;

import lombok.AllArgsConstructor;
import org.example.plantsmap.dto.Plant;
import org.example.plantsmap.enums.KingdomType;
import org.example.plantsmap.generated.tables.pojos.MPlant;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlantEntityTransformer {
    private final UserService userService;
    private final CommentService commentService;

    public Plant mapEntityToDto(MPlant mPlant) {
        return Plant.builder()
                .id(mPlant.getId())
                .name(mPlant.getName())
                .coordinate(mPlant.getCoordinate())
                .comments(commentService.getByPlantId(mPlant.getId()))
                .description(mPlant.getDescription())
                .filePath(mPlant.getFilePath())
                .type(KingdomType.valueOf(mPlant.getKingdomType()))
                .user(userService.getById(mPlant.getUserId()))
                .build();
    }

}
