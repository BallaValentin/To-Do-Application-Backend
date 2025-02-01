package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDao;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
@Profile("!jpa")
public class ToDoServiceImpl implements ToDoService {

    private final ToDoDao toDoDao;

    @Autowired
    public ToDoServiceImpl(ToDoDao toDoDao) {
        this.toDoDao = toDoDao;
    }

    @Override
    public void createToDo(ToDo toDo) throws IllegalArgumentException {
        validateToDo(toDo);
        toDoDao.create(toDo);
    }

    @Override
    public void updateToDo(ToDo toDo) throws EntityNotFoundException, IllegalArgumentException {
        validateId(toDo.getId());
        validateToDo(toDo);
        toDoDao.update(toDo);
    }

    @Override
    public void deleteToDo(Long id) throws EntityNotFoundException {
        validateId(id);
        toDoDao.deleteById(id);
    }

    @Override
    public Integer deleteTodosBetweenDates(Optional<Date> beforeDate, Optional<Date> afterDate) throws IllegalArgumentException {
        return null;
    }

    @Override
    public ToDo getById(Long id) throws EntityNotFoundException {
        Optional<ToDo> toDo = toDoDao.findById(id);
        if (toDo.isEmpty()) {
            throw new EntityNotFoundException("ToDo with id " + id + " not found");
        }
        return toDo.get();
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

    private void validateId(Long id) {
        getById(id);
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

    }

    @Override
    public void deleteDetailById(Long toDoId, Long toDoDetailId) throws EntityNotFoundException {

    }
}
