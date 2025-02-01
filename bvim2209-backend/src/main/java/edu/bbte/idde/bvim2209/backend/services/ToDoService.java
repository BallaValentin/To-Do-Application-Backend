package edu.bbte.idde.bvim2209.backend.services;

import edu.bbte.idde.bvim2209.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.backend.model.ToDo;

import java.text.ParseException;
import java.util.Collection;

public interface ToDoService {
    void createToDo(ToDo toDo) throws ParseException, IllegalArgumentException;

    void updateToDo(ToDo toDo) throws EntityNotFoundException, ParseException;

    void deleteToDo(Long id) throws EntityNotFoundException, ParseException;

    ToDo findById(Long id) throws EntityNotFoundException;

    Collection<ToDo> findAll();

    public Integer getLogQueryCount();

    public Integer getLogUpdateCount();
}
