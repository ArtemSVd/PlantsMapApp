package org.example.plantsmap.service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.example.plantsmap.dto.Plant;
import org.example.plantsmap.dto.PlantsRequestParams;
import org.example.plantsmap.generated.tables.pojos.MPlant;
import org.example.plantsmap.generated.tables.records.MPlantRecord;
import org.example.plantsmap.security.UserContext;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.Record1;
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

    private final UserContext userContext;

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

        val kingdomTypeFilter = listParams.getKingdomTypes();
        condition = kingdomTypeFilter != null ? condition.and(M_PLANT.KINGDOM_TYPE.in(kingdomTypeFilter)) : condition;

        val nameFilter = listParams.getName();
        condition = nameFilter != null ? condition.and(M_PLANT.NAME.likeIgnoreCase("%" + nameFilter + "%")) : condition;

        val excludedIdsFromDeviceFilter = listParams.getExcludedPlantIds();

        SelectConditionStep<Record1<Integer>> excludeSelect = jooq
                .select(M_PLANT.ID)
                .from(M_PLANT)
                .where(M_PLANT.ID_FROM_DEVICE.in(excludedIdsFromDeviceFilter).and(M_PLANT.USER_ID.eq(userContext.getUser().getId())));

        List<Integer> excludedIds = excludeSelect.fetchInto(Integer.class);

        condition = excludedIdsFromDeviceFilter != null && !excludedIdsFromDeviceFilter.isEmpty() ? condition.and(M_PLANT.ID.notIn(excludedIds)) : condition;

        return jooq
                .selectFrom(M_PLANT)
                .where(condition);
    }

}
