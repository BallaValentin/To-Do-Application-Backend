package edu.bbte.idde.bvim2209.backend.services;

import edu.bbte.idde.bvim2209.backend.conf.ConfigurationFactory;
import edu.bbte.idde.bvim2209.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.backend.model.ToDo;
import edu.bbte.idde.bvim2209.backend.repo.DaoFactory;
import edu.bbte.idde.bvim2209.backend.repo.ToDoDao;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
public class ToDoServiceImpl implements ToDoService {
    private final transient ToDoDao toDoDao;
    private final Boolean showVersion = ConfigurationFactory.getConfiguration().getShowVersion();

    public ToDoServiceImpl() {

        toDoDao = DaoFactory.getInstance().getToDoDao();
    }

    @Override
    public void createToDo(ToDo toDo) throws IllegalArgumentException {
        toDo.setVersion(1L);
        validateToDo(toDo);
        toDoDao.create(toDo);
    }

    @Override
    public void updateToDo(ToDo toDo) throws EntityNotFoundException, IllegalArgumentException {
        ToDo toDoToUpdate = toDoDao.findById(toDo.getId());
        toDo.setVersion(toDoToUpdate.getVersion() + 1);
        validateToDo(toDo);
        toDoDao.update(toDo);
    }

    @Override
    public void deleteToDo(Long id) throws EntityNotFoundException {
        toDoDao.delete(id);
    }

    @Override
    public ToDo findById(Long id) throws EntityNotFoundException {
        ToDo toDo = toDoDao.findById(id);
        log.info("Show version: {}", showVersion);
        if (!showVersion) {
            toDo.setVersion(null);
        }
        return toDo;
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
        Collection<ToDo> toDos = toDoDao.findAll();

        if (!showVersion) {
            return toDos.stream().peek(toDo -> toDo.setVersion(null)).collect(Collectors.toList());
        }

        return toDos;
    }
}
