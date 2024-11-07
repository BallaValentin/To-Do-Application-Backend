package edu.bbte.idde.bvim2209.repo.jdbc;

import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.bvim2209.util.PropertyProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceFactory {
    private static HikariDataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(DataSourceFactory.class);

    public static synchronized HikariDataSource getDataSource() {
        if (dataSource == null) {
            logger.info("Setting up database connection parameters...");
            dataSource = new HikariDataSource();
            dataSource.setJdbcUrl(PropertyProvider.getProperty("JDBC_URL"));
            dataSource.setUsername(PropertyProvider.getProperty("USERNAME"));
            dataSource.setPassword(PropertyProvider.getProperty("PASSWORD"));
            dataSource.setMaximumPoolSize(10);
        }
        return  dataSource;
    }
}
