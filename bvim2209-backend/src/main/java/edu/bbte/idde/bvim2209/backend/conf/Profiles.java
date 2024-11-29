package edu.bbte.idde.bvim2209.backend.conf;

public class Profiles {
    private InMemoryConfiguration inMemoryConfiguration;

    private JdbcConfiguration jdbcConfiguration;

    public InMemoryConfiguration getInMemoryConfiguration() {
        return inMemoryConfiguration;
    }

    public void setInMemoryConfiguration(InMemoryConfiguration inMemoryConfiguration) {
        this.inMemoryConfiguration = inMemoryConfiguration;
    }

    public JdbcConfiguration getJdbcConfiguration() {
        return jdbcConfiguration;
    }

    public void setJdbcConfiguration(JdbcConfiguration jdbcConfiguration) {
        this.jdbcConfiguration = jdbcConfiguration;
    }
}
