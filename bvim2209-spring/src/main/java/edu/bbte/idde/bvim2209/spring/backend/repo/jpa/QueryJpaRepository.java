package edu.bbte.idde.bvim2209.spring.backend.repo.jpa;

import edu.bbte.idde.bvim2209.spring.backend.model.Query;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QueryJpaRepository extends JpaRepository<Query, Long> {
}
