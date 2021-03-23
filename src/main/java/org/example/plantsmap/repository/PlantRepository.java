package org.example.plantsmap.repository;

import lombok.AllArgsConstructor;
import org.example.plantsmap.dto.Plant;
import org.example.plantsmap.generated.tables.daos.MPlantDao;
import org.example.plantsmap.generated.tables.pojos.MPlant;
import org.jooq.DSLContext;

import static org.example.plantsmap.generated.Sequences.SEQ_PLANT;
import static org.example.plantsmap.generated.Tables.M_PLANT;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@AllArgsConstructor
public class PlantRepository {

    private final DSLContext context;

    private final MPlantDao dao;

    public int create(Plant plant) {
        MPlant mPlant = new MPlant();
        mPlant.setId(context.nextval(SEQ_PLANT).intValue());
        mPlant.setIdFromDevice(plant.getId());
        mPlant.setName(plant.getName());
        mPlant.setDescription(plant.getDescription());
        mPlant.setKingdomType(plant.getType().name());
        mPlant.setCreatedDate(LocalDateTime.now());
        mPlant.setUserId(plant.getUser().getId());
        if (plant.getCoordinate() != null) {
            mPlant.setLatitude(plant.getCoordinate().getLatitude());
            mPlant.setLongitude(plant.getCoordinate().getLongitude());
        }
        mPlant.setFilePath(plant.getFilePath());

        dao.insert(mPlant);

        return mPlant.getId();
    }

    public int update(Plant plant, MPlant mPlant) {
        mPlant.setName(plant.getName());
        mPlant.setKingdomType(plant.getType().name());
        mPlant.setDescription(plant.getDescription());
        mPlant.setUpdatedDate(LocalDateTime.now());

        dao.update(mPlant);

        return mPlant.getId();
    }

    public MPlant getByIdFromDeviceAndUserId(Integer idFromDevice, Integer userId) {
        return context.select(M_PLANT.fields())
                .from(M_PLANT)
                .where(M_PLANT.ID_FROM_DEVICE.eq(idFromDevice)
                        .and(M_PLANT.USER_ID.eq(userId)))
                .fetchOneInto(MPlant.class);

    }

    public MPlant getById(Integer id) {
        return context.select(M_PLANT.fields())
                .from(M_PLANT)
                .where(M_PLANT.ID.eq(id))
                .fetchOneInto(MPlant.class);
    }

}
