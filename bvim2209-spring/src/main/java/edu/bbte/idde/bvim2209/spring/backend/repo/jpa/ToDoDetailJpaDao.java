package edu.bbte.idde.bvim2209.spring.backend.repo.jpa;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoDetailJpaDao extends JpaRepository<ToDoDetail, Long> {
}
