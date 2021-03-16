package org.example.plantsmap.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.example.plantsmap.enums.KingdomType;

import java.util.List;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Log4j2
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
