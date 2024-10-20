package edu.bbte.idde.bvim2209.services;

import edu.bbte.idde.bvim2209.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.model.ToDo;
import edu.bbte.idde.bvim2209.repo.DaoFactory;
import edu.bbte.idde.bvim2209.repo.ToDoDao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class ToDoServiceImpl implements ToDoService{
    private final ToDoDao toDoDao;
    private static Long nextId = 1L;

    public ToDoServiceImpl() {
        toDoDao = DaoFactory.getInstance().getToDoDao();
    }

    @Override
    public void createToDo(String title, String description, String dueDate, String importanceLevel) throws ParseException, IllegalArgumentException {
        if(title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty or null");
        }
        if(description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dueDateNew = dateFormat.parse(dueDate);
        Integer integer;
        integer = Integer.parseInt(importanceLevel);
        ToDo toDo = new ToDo(nextId++, title, description, dueDateNew, integer);
        toDoDao.create(toDo);
    }

    @Override
    public void updateToDo(Long id, String title, String description, String dueDate, String importanceLevel) throws EntityNotFoundException, ParseException, IllegalArgumentException {
        if(title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty or null");
        }
        if(description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date dueDateNew = dateFormat.parse(dueDate);
        Integer integer;
        integer = Integer.parseInt(importanceLevel);
        ToDo toDo = new ToDo(id, title, description, dueDateNew, integer);
        toDoDao.update(toDo);
    }

    @Override
    public void deleteToDo(Long id) throws EntityNotFoundException{
        toDoDao.delete(id);
    }

    @Override
    public Collection<ToDo> findAll() {
        return toDoDao.findAll();
    }
}
