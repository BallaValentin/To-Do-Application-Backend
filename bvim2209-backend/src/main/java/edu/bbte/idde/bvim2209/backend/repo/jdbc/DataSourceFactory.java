package edu.bbte.idde.bvim2209.backend.repo.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.bvim2209.backend.conf.ConfigurationFactory;
import edu.bbte.idde.bvim2209.backend.conf.JdbcConfiguration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceFactory {
    private static HikariDataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);

    public static synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            logger.info("Setting up database connection parameters...");
            Object activeConfiguration = ConfigurationFactory.getActiveProfileConfig();
            if (activeConfiguration instanceof JdbcConfiguration jdbcConfiguration) {
                dataSource = new HikariDataSource();
                dataSource.setJdbcUrl(jdbcConfiguration.getDatabaseConfig().getUrl());
                dataSource.setUsername(jdbcConfiguration.getDatabaseConfig().getUsername());
                dataSource.setPassword(jdbcConfiguration.getDatabaseConfig().getPassword());
                dataSource.setMaximumPoolSize(jdbcConfiguration.getDatabaseConfig().getConnectionPoolSize());
                dataSource.setDriverClassName(jdbcConfiguration.getDatabaseConfig().getDriverClassName());
            } else {
                logger.warn("In-memory configuration selected. No JDBC data source configured.");
            }
        }
        return dataSource;
    }
}
