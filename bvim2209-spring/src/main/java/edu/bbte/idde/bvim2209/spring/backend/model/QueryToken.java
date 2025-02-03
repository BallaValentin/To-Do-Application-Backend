package edu.bbte.idde.bvim2209.spring.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "db_query_token")
@Data
public class QueryToken extends BaseEntity {
    private String token;
    private String operationType;
    private String entityName;
}
