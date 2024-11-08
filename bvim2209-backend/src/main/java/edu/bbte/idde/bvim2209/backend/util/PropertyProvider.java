package edu.bbte.idde.bvim2209.backend.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyProvider {
    private static final Logger logger = LoggerFactory.getLogger(PropertyProvider.class);
    private static final String PROP_FILE_NAME = "application";
    private static Properties properties;

    static {
        properties = new Properties();

        String propertiesResourceName = buildPropertiesResourceName();
        logger.info("Attempting to load properties from {}", propertiesResourceName);

        try (InputStream inputStream = PropertyProvider.class.getResourceAsStream(propertiesResourceName)) {
            properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Error loading properties", e);
        }
    }

    private static String buildPropertiesResourceName() {
        logger.info("Loading properties");
        StringBuilder sb = new StringBuilder();
        sb.append('/').append(PROP_FILE_NAME);

        String profile = System.getProperty("profile");
        logger.info("Determined profile: {}", profile);
        if (profile != null && !profile.isEmpty()) {
            sb.append('-').append(profile);
        }

        sb.append(".properties");
        return sb.toString();
    }

    public static String getProperty(final String key) {
        return properties.getProperty(key);
    }
}
