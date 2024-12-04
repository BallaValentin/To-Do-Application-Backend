package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;

import java.util.Collection;

public interface ToDoService {
    void createToDo(ToDo toDo);

    void updateToDo(ToDo toDo);

    void deleteToDo(Long id);

    ToDo findById(Long id);

    Collection<ToDo> findAll();
}
