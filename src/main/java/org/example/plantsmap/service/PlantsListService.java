package org.example.plantsmap.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.example.plantsmap.dto.Plant;
import org.example.plantsmap.dto.PlantsRequestParams;
import org.example.plantsmap.generated.tables.pojos.MPlant;
import org.example.plantsmap.generated.tables.records.MPlantRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SelectConditionStep;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.jooq.impl.DSL.noCondition;
import static org.example.plantsmap.generated.tables.MPlant.M_PLANT;

@Log4j2
@Service
@AllArgsConstructor
public class PlantsListService {

    private final DSLContext jooq;
    private final PlantEntityTransformer transformer;

    public List<Plant> list(PlantsRequestParams listParams) {
        log.info("read plants by params: " + listParams);

        val listQuery = getSelect(listParams);

        return listQuery
                .fetchInto(MPlant.class)
                .stream()
                .map(transformer::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private SelectConditionStep<MPlantRecord> getSelect(PlantsRequestParams listParams) {
        Condition condition = noCondition();

        val kingdomTypeFilter = listParams.getKingdomType();
        condition = kingdomTypeFilter != null ? condition.and(M_PLANT.KINGDOM_TYPE.eq(kingdomTypeFilter)) : condition;

        val nameFilter = listParams.getName();
        condition = nameFilter != null ? condition.and(M_PLANT.NAME.likeIgnoreCase(nameFilter)) : condition;

        return jooq
                .selectFrom(M_PLANT)
                .where(condition);
    }

}
