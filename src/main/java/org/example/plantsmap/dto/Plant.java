package org.example.plantsmap.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.example.plantsmap.enums.KingdomType;

import java.util.List;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Plant {
    private Integer id;

    private String fileName;

    private String filePath;

    private String name;

    private String description;

    private KingdomType type;

    private User user;

    private Coordinate coordinate;

    private List<Comment> comments;
}
