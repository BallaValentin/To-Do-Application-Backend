package edu.bbte.idde.bvim2209.backend.conf;

public class Configuration {
    private String daoImplementation;
    private DatabaseConfig databaseConfig;

    private Configuration jdbcConfiguration;
    private Configuration inMemoryConfiguration;

    public String getDaoImplementation() {
        return daoImplementation;
    }

    public void setDaoImplementation(String daoImplementation) {
        this.daoImplementation = daoImplementation;
    }

    public DatabaseConfig getDatabaseConfig() {
        return databaseConfig;
    }

    public void setDatabaseConfig(DatabaseConfig databaseConfig) {
        this.databaseConfig = databaseConfig;
    }

    public Configuration getJdbcConfiguration() {
        return jdbcConfiguration;
    }

    public void setJdbcConfiguration(Configuration jdbcConfiguration) {
        this.jdbcConfiguration = jdbcConfiguration;
    }

    public Configuration getInMemoryConfiguration() {
        return inMemoryConfiguration;
    }

    public void setInMemoryConfiguration(Configuration inMemoryConfiguration) {
        this.inMemoryConfiguration = inMemoryConfiguration;
    }
}
