package edu.bbte.idde.bvim2209.spring.backend.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "custom")
@Getter
@Setter
@NoArgsConstructor
public class CustomConfigLoader {
    private Integer timeWindow;
}
