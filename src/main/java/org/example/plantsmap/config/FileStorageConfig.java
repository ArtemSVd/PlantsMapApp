package org.example.plantsmap.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
public class FileStorageConfig {
    private String location;
}
