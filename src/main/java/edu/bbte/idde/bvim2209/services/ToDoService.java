package edu.bbte.idde.bvim2209.services;

import edu.bbte.idde.bvim2209.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.model.ToDo;

import java.text.ParseException;
import java.util.Collection;

public interface ToDoService {
    public void createToDo(String title, String description, String dueDate, String importanceLevel) throws ParseException;
    public void updateToDo(Long id, String title, String description, String dueDate, String importanceLeve) throws EntityNotFoundException, ParseException;
    public void deleteToDo(Long id) throws EntityNotFoundException, ParseException;
    public Collection<ToDo> findAll();
}
