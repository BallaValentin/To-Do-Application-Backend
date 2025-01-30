package edu.bbte.idde.bvim2209.backend.conf;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Configuration {
    private Long limit;
    private String activeProfile;
    private JdbcConfiguration jdbcConfiguration;
}
