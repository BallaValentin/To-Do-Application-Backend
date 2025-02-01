package edu.bbte.idde.bvim2209.spring.backend.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "custom")
@Getter
@Setter
public class CustomConfiguration {
    private Boolean failOnDeleteMissing;
    private Boolean isDeleteMissingFatal;
}
