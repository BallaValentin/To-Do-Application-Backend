package edu.bbte.idde.bvim2209.spring.backend.repo;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ToDoDetailDao extends Dao<ToDoDetail> {
    Page<ToDoDetail> findByToDoId(Long id, Pageable pageable);
}
