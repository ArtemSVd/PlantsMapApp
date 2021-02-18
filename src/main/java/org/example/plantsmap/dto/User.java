package org.example.plantsmap.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class User {
    private Integer id;
    private String deviceName;
    private String name;
}
