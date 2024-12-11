package edu.bbte.idde.bvim2209.spring.backend.repo;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;

import java.util.Collection;

public interface ToDoDao extends Dao<ToDo> {
    public Collection<ToDo> findByImportance(Integer levelOfImportance);
}
