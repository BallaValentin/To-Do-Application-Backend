package edu.bbte.idde.bvim2209.spring.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "db_query_token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryToken extends BaseEntity {
    private String token;
    private String operationType;
    private String entityName;
}
