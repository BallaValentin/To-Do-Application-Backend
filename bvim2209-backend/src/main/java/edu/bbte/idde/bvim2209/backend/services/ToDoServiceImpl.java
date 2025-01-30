package edu.bbte.idde.bvim2209.backend.services;

import edu.bbte.idde.bvim2209.backend.conf.Configuration;
import edu.bbte.idde.bvim2209.backend.conf.ConfigurationFactory;
import edu.bbte.idde.bvim2209.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.backend.model.ToDo;
import edu.bbte.idde.bvim2209.backend.repo.DaoFactory;
import edu.bbte.idde.bvim2209.backend.repo.ToDoDao;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

public class ToDoServiceImpl implements ToDoService {
    @Getter
    @Setter
    private Long limit;
    private final transient ToDoDao toDoDao;

    public ToDoServiceImpl() {
        toDoDao = DaoFactory.getInstance().getToDoDao();
        limit = ConfigurationFactory.getConfiguration().getLimit();
        if (limit == null) {
            limit = 5L;
        }
    }

    @Override
    public void createToDo(ToDo toDo) throws IllegalArgumentException {
        validateToDo(toDo);
        toDoDao.create(toDo);
    }

    @Override
    public void updateToDo(ToDo toDo) throws EntityNotFoundException, IllegalArgumentException {
        validateToDo(toDo);
        toDoDao.update(toDo);
    }

    @Override
    public void deleteToDo(Long id) throws EntityNotFoundException {
        toDoDao.delete(id);
    }

    @Override
    public ToDo findById(Long id) throws EntityNotFoundException {
        return toDoDao.findById(id);
    }

    private void validateToDo(ToDo toDo) {
        validateTitle(toDo);
        validateDescription(toDo);
        validateDueDate(toDo);
        validateImportanceLevel(toDo);
    }

    private void validateTitle(ToDo toDo) {
        if (toDo.getTitle() == null || toDo.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty or null");
        }
    }

    private void validateDescription(ToDo toDo) {
        if (toDo.getDescription() == null || toDo.getDescription().isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or empty");
        }
    }

    private void validateDueDate(ToDo toDo) {
        if (toDo.getDueDate() == null) {
            throw new IllegalArgumentException("Due date cannot be null");
        }
    }

    private void validateImportanceLevel(ToDo toDo) {
        if (toDo.getLevelOfImportance() == null) {
            throw new IllegalArgumentException("Importance level cannot be null");
        }
    }

    @Override
    public Collection<ToDo> findAll() {
        return toDoDao.findAll(limit);
    }
}
