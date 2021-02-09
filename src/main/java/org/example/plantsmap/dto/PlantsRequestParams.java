package org.example.plantsmap.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class PlantsRequestParams {
    private String kingdomType;
    private String name;

    // todo: продумать фильтр по координатам
}
