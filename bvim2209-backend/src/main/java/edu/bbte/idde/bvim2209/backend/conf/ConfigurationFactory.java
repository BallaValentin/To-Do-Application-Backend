package edu.bbte.idde.bvim2209.backend.conf;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.bvim2209.backend.exceptions.ConfigurationException;

import java.io.IOException;
import java.io.InputStream;

public class ConfigurationFactory {
    private static final String CONFIG_FILE = "config.json";
    private static final MainConfiguration mainConfiguration;

    static {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE)) {
            mainConfiguration = mapper.readValue(input, MainConfiguration.class);
        } catch (IOException exception) {
            throw new ConfigurationException("Failed to load configuration file", exception);
        }
    }

    public static MainConfiguration getMainConfiguration() {
        return mainConfiguration;
    }

    public static Object getActiveProfileConfig() {
        String activeProfile = System.getenv("ACTIVE_PROFILE");
        if ("jdbc".equals(activeProfile)) {
            return mainConfiguration.getProfiles().getJdbcConfiguration();
        } else {
            return mainConfiguration.getProfiles().getInMemoryConfiguration();
        }
    }
}
