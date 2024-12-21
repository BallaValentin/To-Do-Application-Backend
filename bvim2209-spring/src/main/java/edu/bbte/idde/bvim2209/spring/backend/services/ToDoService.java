package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;

import java.text.ParseException;
import java.util.Collection;

public interface ToDoService {
    void createToDo(ToDo toDo) throws ParseException, IllegalArgumentException;

    void updateToDo(ToDo toDo) throws EntityNotFoundException, ParseException;

    void deleteToDo(Long id) throws EntityNotFoundException, ParseException;

    ToDo getById(Long id) throws EntityNotFoundException;

    Collection<ToDo> findAll();

    Collection<ToDo> findByImportance(Integer importanceLevel);

    void addDetailToToDo(Long id, ToDoDetail toDoDetail) throws EntityNotFoundException;

    void deleteDetailById(Long toDoId, Long toDoDetailId) throws EntityNotFoundException;
}
