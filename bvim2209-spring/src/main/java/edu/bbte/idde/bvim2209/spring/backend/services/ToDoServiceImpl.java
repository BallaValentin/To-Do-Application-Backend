package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDao;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDetailDao;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class ToDoServiceImpl implements ToDoService {

    private final ToDoDao toDoDao;
    private final ToDoDetailDao toDoDetailDao;

    @Autowired
    public ToDoServiceImpl(ToDoDao toDoDao, ToDoDetailDao toDoDetailDao) {
        this.toDoDao = toDoDao;
        this.toDoDetailDao = toDoDetailDao;
    }

    @Override
    public void createToDo(ToDo toDo) throws IllegalArgumentException {
        validateToDo(toDo);
        toDoDao.saveAndFlush(toDo);
    }

    @Override
    public void updateToDo(ToDo toDo) throws EntityNotFoundException, IllegalArgumentException {
        validateToDo(toDo);
        toDoDao.update(toDo);
    }

    @Override
    public void deleteToDo(Long id) throws EntityNotFoundException {
        toDoDao.deleteById(id);
    }

    @Override
    public ToDo  getById(Long id) throws EntityNotFoundException {
        return toDoDao.getById(id);
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
        return toDoDao.findAll();
    }

    @Override
    public Collection<ToDo> findByImportance(Integer levelOfImportance) {
        return toDoDao.findByLevelOfImportance(levelOfImportance);
    }

    @Override
    public Collection<ToDoDetail> getDetails(Long id) {
        ToDo toDo = getById(id);
        return toDo.getDetails();
    }

    @Override
    public void addDetailToToDo(Long id, ToDoDetail toDoDetail) throws EntityNotFoundException {
        ToDo toDo = getById(id);
        toDo.getDetails().add(toDoDetail);
        toDoDetail.setToDo(toDo);
        toDoDetailDao.saveAndFlush(toDoDetail);
        toDoDao.update(toDo);
    }

    @Override
    public void deleteDetailById(Long toDoId, Long toDoDetailId) throws EntityNotFoundException {
        ToDo toDo = getById(toDoId);
        ToDoDetail toDoDetail = toDoDetailDao.getById(toDoDetailId);
        toDo.getDetails().remove(toDoDetail);
        toDoDao.update(toDo);
        toDoDetailDao.deleteById(toDoDetailId);
    }
}
