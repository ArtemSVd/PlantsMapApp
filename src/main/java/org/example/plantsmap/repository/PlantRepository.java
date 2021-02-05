package org.example.plantsmap.repository;


import lombok.AllArgsConstructor;
import org.example.plantsmap.dto.Plant;
import org.example.plantsmap.generated.tables.daos.MPlantDao;
import org.example.plantsmap.generated.tables.pojos.MPlant;
import org.jooq.DSLContext;
import static org.example.plantsmap.generated.Sequences.SEQ_PLANT;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class PlantRepository {

    private final DSLContext context;

    private final MPlantDao dao;

    public void create(Plant plant) {
        MPlant mPlant = new MPlant();
        mPlant.setId(context.nextval(SEQ_PLANT).intValue());
        mPlant.setIdFromDevice(plant.getId());
        mPlant.setName(plant.getName());
        mPlant.setDescription(plant.getDescription());
        mPlant.setKingdomType(plant.getType().name());
        mPlant.setCreatedDate(LocalDateTime.now());
        mPlant.setUserId(plant.getUser().getId());
        mPlant.setCoordinate(plant.getCoordinate());
        mPlant.setFilePath(plant.getFilePath());

        dao.insert(mPlant);
    }



}
