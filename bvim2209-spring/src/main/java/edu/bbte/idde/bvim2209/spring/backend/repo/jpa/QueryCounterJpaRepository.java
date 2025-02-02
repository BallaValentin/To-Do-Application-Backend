package edu.bbte.idde.bvim2209.spring.backend.repo.jpa;

import edu.bbte.idde.bvim2209.spring.backend.model.QueryCounter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QueryCounterJpaRepository extends JpaRepository<QueryCounter, Long> {
    Optional<QueryCounter> findByQueryTypeAndEntityName(String queryType, String entityName);
}
