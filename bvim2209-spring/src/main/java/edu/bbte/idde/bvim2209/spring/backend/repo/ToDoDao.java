package edu.bbte.idde.bvim2209.spring.backend.repo;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.User;

import java.util.Collection;
import java.util.Optional;

public interface ToDoDao extends Dao<ToDo> {
    Collection<ToDo> findByLevelOfImportance(Integer levelOfImportance);

    Optional<ToDo> findByIdAndUser(Long id, User user);
}
