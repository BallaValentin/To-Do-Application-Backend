package edu.bbte.idde.bvim2209.spring.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "db_query")
public class Query extends BaseEntity {
    private String searchQuery;
    private Instant searchDate;
}
