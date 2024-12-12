package edu.bbte.idde.bvim2209.backend.conf;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatabaseConfig {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private Integer connectionPoolSize;
}
