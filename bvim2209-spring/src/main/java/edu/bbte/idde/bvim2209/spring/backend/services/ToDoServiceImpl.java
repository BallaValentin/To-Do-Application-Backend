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
    @Caching(evict =
            {@CacheEvict(value = "todos", key = "'allTodos'"), @CacheEvict(
                    value = "todos", key = "'allTodos' + #toDo.levelOfImportance")
        })
    public void createToDo(ToDo toDo) throws IllegalArgumentException {
        validateToDo(toDo);
        toDoDao.saveAndFlush(toDo);
    }

    @Override
    @Caching(evict =
            {@CacheEvict(value = "todos", key = "'allTodos'"), @CacheEvict(
                    value = "todos", key = "'allTodos' + #toDo.levelOfImportance"),
             @CacheEvict(value = "todos", key = "#toDo.id")
        })
    public void updateToDo(ToDo toDo) throws EntityNotFoundException, IllegalArgumentException {
        validateToDo(toDo);
        toDoDao.update(toDo);
    }

    @Override
    @Caching(evict =
            {@CacheEvict(value = "todos", key = "'allTodos'"), @CacheEvict(
                    value = "todos", key = "'allTodos' + #toDo.levelOfImportance"),
             @CacheEvict(value = "todos", key = "#toDo.id")
        })
    public void deleteToDo(ToDo toDo) throws EntityNotFoundException {
        toDoDao.deleteById(toDo.getId());
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

    @Override
    @Cacheable(value = "todos", key = "'allTodos'")
    public Collection<ToDo> findAll() {
        return toDoDao.findAll();
    }

    @Override
    @Cacheable(value = "todos", key = "'allTodos' + #levelOfImportance")
    public Collection<ToDo> findByImportance(Integer levelOfImportance) {
        return toDoDao.findByLevelOfImportance(levelOfImportance);
    }

    @Override
    public Collection<ToDoDetail> getDetails(ToDo toDo) {
        return toDo.getDetails();
    }

    @Override
    @Caching(evict =
            {@CacheEvict(value = "todos", key = "'allTodos'"), @CacheEvict(
                    value = "todos", key = "'allTodos' + toDo.levelOfImportance"),
             @CacheEvict(value = "todos", key = "#toDo.id")
        })
    public void addDetailToToDo(ToDo toDo, ToDoDetail toDoDetail) throws EntityNotFoundException {
        toDo.getDetails().add(toDoDetail);
        toDoDetail.setToDo(toDo);
        toDoDetailDao.saveAndFlush(toDoDetail);
        toDoDao.update(toDo);
    }

    @Override
    @Caching(evict =
            {@CacheEvict(value = "todos", key = "'allTodos'"), @CacheEvict(
                    value = "todos", key = "'allTodos' + #toDo.levelOfImportance"),
             @CacheEvict(value = "todos", key = "#toDo.id")
        })
    public void deleteDetailById(ToDo toDo, Long toDoDetailId) throws EntityNotFoundException {
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
