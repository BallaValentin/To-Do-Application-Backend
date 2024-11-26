package edu.bbte.idde.bvim2209.backend.repo.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.bvim2209.backend.conf.Configuration;
import edu.bbte.idde.bvim2209.backend.conf.ConfigurationFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceFactory {
    private static HikariDataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);

    public static synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            logger.info("Setting up database connection parameters...");
            Configuration activeConfiguration = ConfigurationFactory.getActiveProfileConfig();
            if ("jdbc".equals(activeConfiguration.getDaoImplementation())) {
                dataSource = new HikariDataSource();
                dataSource.setJdbcUrl(activeConfiguration.getDatabaseConfig().getUrl());
                dataSource.setUsername(activeConfiguration.getDatabaseConfig().getUsername());
                dataSource.setPassword(activeConfiguration.getDatabaseConfig().getPassword());
                dataSource.setMaximumPoolSize(activeConfiguration.getDatabaseConfig().getConnectionPoolSize());
                dataSource.setDriverClassName(activeConfiguration.getDatabaseConfig().getDriverClassName());
            } else {
                logger.warn("In-memory configuration selected. No JDBC data source configured.");
            }
        }
        return dataSource;
    }
}
