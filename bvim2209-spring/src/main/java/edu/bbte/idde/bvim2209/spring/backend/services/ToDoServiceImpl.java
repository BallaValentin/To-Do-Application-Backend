package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDao;
import edu.bbte.idde.bvim2209.spring.backend.repo.ToDoDetailDao;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

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
    @CacheEvict(value = "todos", key = "'allTodos'")
    public void createToDo(ToDo toDo) throws IllegalArgumentException {
        validateToDo(toDo);
        toDoDao.saveAndFlush(toDo);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "todos", key = "'allTodos'"),
            @CacheEvict(value = "todos", key = "#toDo.id")
    })
    public void updateToDo(ToDo toDo) throws EntityNotFoundException, IllegalArgumentException {
        validateId(toDo.getId());
        validateToDo(toDo);
        toDoDao.update(toDo);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "todos", key = "'allTodos'"),
            @CacheEvict(value = "todos", key="#id")
    })
    public void deleteToDo(Long id) throws EntityNotFoundException {
        validateId(id);
        toDoDao.deleteById(id);
    }

    @Override
    @Cacheable(value = "todos", key = "#id")
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
    @Cacheable(value = "todos", key="'allTodos'")
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
    @Caching(evict = {
            @CacheEvict(value = "todos", key = "'allTodos'"),
            @CacheEvict(value = "todos", key = "#id")
    })
    public void addDetailToToDo(Long id, ToDoDetail toDoDetail) throws EntityNotFoundException {
        ToDo toDo = getById(id);
        toDo.getDetails().add(toDoDetail);
        toDoDetail.setToDo(toDo);
        toDoDetailDao.saveAndFlush(toDoDetail);
        toDoDao.update(toDo);
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "todos", key = "'allTodos'"),
            @CacheEvict(value = "todos", key = "#toDoId")
    })
    public void deleteDetailById(Long toDoId, Long toDoDetailId) throws EntityNotFoundException {
        ToDo toDo = getById(toDoId);
        Optional<ToDoDetail> toDoDetail = toDoDetailDao.findById(toDoDetailId);
        if (toDoDetail.isEmpty()) {
            throw new EntityNotFoundException("ToDoDetail with id " + toDoDetailId + " not found");
        } else {
            toDo.getDetails().remove(toDoDetail.get());
            toDoDao.update(toDo);
            toDoDetailDao.deleteById(toDoDetailId);
        }
    }
}
