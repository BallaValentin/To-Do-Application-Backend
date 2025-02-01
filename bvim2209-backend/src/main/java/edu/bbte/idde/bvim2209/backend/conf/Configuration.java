package edu.bbte.idde.bvim2209.backend.conf;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Configuration {
    private Boolean logQueries;
    private Boolean logUpdates;
    private String activeProfile;
    private JdbcConfiguration jdbcConfiguration;
}
