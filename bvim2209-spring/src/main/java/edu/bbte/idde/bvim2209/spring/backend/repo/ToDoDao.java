package edu.bbte.idde.bvim2209.spring.backend.repo;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public interface ToDoDao extends Dao<ToDo> {
    Collection<ToDo> findByLevelOfImportance(Integer levelOfImportance);

    Collection<ToDo> findByUser(User user);
}
