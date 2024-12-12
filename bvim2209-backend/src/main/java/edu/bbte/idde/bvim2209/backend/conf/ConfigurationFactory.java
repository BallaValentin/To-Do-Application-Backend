package edu.bbte.idde.bvim2209.backend.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.bvim2209.backend.exceptions.ConfigurationException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ConfigurationFactory {
    private static final String CONFIG_FILE = "config.json";
    @Getter
    private static final Configuration configuration;
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationFactory.class);

    static {
        logger.info("Loading configuration file: " + CONFIG_FILE);
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE)) {
            configuration = mapper.readValue(input, Configuration.class);
            logger.info("The main configuration has been loaded");
        } catch (IOException exception) {
            logger.error(exception.getMessage(), exception);
            throw new ConfigurationException("Failed to load configuration file", exception);
        }
    }

    public static JdbcConfiguration getJdbcConfiguration() {
        return configuration.getJdbcConfiguration();
    }

    public static String getActiveProfileConfig() {
        String activeProfile = System.getenv("ACTIVE_PROFILE");
        if ("jdbc".equals(activeProfile)) {
            logger.info("Active profile is jdbc");
            return "jdbc";
        } else {
            logger.info("Active profile is not inMemory");
            return "inMemory";
        }
    }
}
