package edu.bbte.idde.bvim2209.backend.conf;

public class JdbcConfiguration {
    private String daoImplementation;
    private DatabaseConfig databaseConfig;

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
}
