package edu.bbte.idde.bvim2209.backend.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.bvim2209.backend.exceptions.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class ConfigurationFactory {
    private static final String CONFIG_FILE = "config.json";
    private static final Configuration mainConfiguration;
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationFactory.class);

    static {
        logger.info("Loading configuration file: " + CONFIG_FILE);
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE)) {
            mainConfiguration = mapper.readValue(input, Configuration.class);
            logger.info("The main configuration has been loaded");
        } catch (IOException exception) {
            logger.error(exception.getMessage(), exception);
            throw new ConfigurationException("Failed to load configuration file", exception);
        }
    }

    public static Configuration getMainConfiguration() {
        return mainConfiguration;
    }

    public static Configuration getActiveProfileConfig() {
        String activeProfile = System.getenv("ACTIVE_PROFILE");
        if ("jdbc".equals(activeProfile)) {
            logger.info("Active profile is jdbc");
            return mainConfiguration.getJdbcConfiguration();
        } else {
            logger.info("Active profile is not inMemory");
            return mainConfiguration.getInMemoryConfiguration();
        }
    }
}
