package edu.bbte.idde.bvim2209.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public abstract class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected Long id;

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        BaseEntity that = (BaseEntity) other;
        return Objects.equals(id, that.id);
    }
}


