package edu.bbte.idde.bvim2209.spring.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "db_query_counter")
@Data
public class QueryCounter extends BaseEntity {
    private String queryType;
    private String entityName;
    private Integer count;

    public QueryCounter(String queryType, String entityName) {
        this.queryType = queryType;
        this.entityName = entityName;
        this.count = 0;
    }

    public QueryCounter() {

    }
}
