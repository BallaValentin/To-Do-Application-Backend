package edu.bbte.idde.bvim2209.services;

import edu.bbte.idde.bvim2209.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.model.ToDo;
import edu.bbte.idde.bvim2209.repo.DaoFactory;
import edu.bbte.idde.bvim2209.repo.ToDoDao;

import java.text.SimpleDateFormat;
import java.util.Collection;

public class ToDoServiceImpl implements ToDoService {
    private final ToDoDao toDoDao;

    public ToDoServiceImpl() {
        toDoDao = DaoFactory.getInstance().getToDoDao();
    }

    @Override
    public void createToDo(ToDo toDo) throws IllegalArgumentException {
        System.out.println("Creating new ToDo");
        if (toDo.getTitle() == null || toDo.getTitle().trim().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty or null");
        }
        if (toDo.getDescription() == null || toDo.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (toDo.getDueDate() == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
        if (toDo.getLevelOfImportance() == null) {
            throw new IllegalArgumentException("Importance level cannot be null");
        }
        toDoDao.create(toDo);
    }

    @Override
    public void updateToDo(ToDo toDo) throws EntityNotFoundException, IllegalArgumentException {
        if (toDo.getTitle() == null || toDo.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty or null");
        }
        if (toDo.getDescription() == null || toDo.getDescription().trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
        if (toDo.getDueDate() == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
        if (toDo.getLevelOfImportance() == null) {
            throw new IllegalArgumentException("Importance level cannot be null");
        }
        toDoDao.update(toDo);
    }

    @Override
    public void deleteToDo(Long id) throws EntityNotFoundException {
        toDoDao.delete(id);
    }

    @Override
    public Collection<ToDo> findAll() {
        return toDoDao.findAll();
    }
}
