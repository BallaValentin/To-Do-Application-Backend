package edu.bbte.idde.bvim2209.backend.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Data
public abstract class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long version;

    protected Long id;

}


