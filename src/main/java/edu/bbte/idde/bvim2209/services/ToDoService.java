package edu.bbte.idde.bvim2209.services;

import edu.bbte.idde.bvim2209.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.model.ToDo;

import java.text.ParseException;
import java.util.Collection;

public interface ToDoService {
    void createToDo(ToDo toDo) throws ParseException, IllegalArgumentException;

    void updateToDo(ToDo toDo) throws EntityNotFoundException, ParseException;

    void deleteToDo(Long id) throws EntityNotFoundException, ParseException;

    Collection<ToDo> findAll();
}
