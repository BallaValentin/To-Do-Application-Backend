package edu.bbte.idde.bvim2209.spring.backend.repo;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface ToDoDao extends Dao<ToDo> {
    Collection<ToDo> findByLevelOfImportance(Integer levelOfImportance);
    Page<ToDo> findByLevelOfImportancePage(Integer levelOfImportance, Pageable pageable);
}
