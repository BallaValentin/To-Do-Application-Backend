package edu.bbte.idde.bvim2209.web.servlet.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class StatDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer logQueriesNumber;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer logUpdatesNumber;
}
