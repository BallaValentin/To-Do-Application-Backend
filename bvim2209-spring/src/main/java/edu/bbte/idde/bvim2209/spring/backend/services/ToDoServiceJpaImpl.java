package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.config.CustomConfigLoader;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.backend.repo.jpa.ToDoDetailJpaRepository;
import edu.bbte.idde.bvim2209.spring.backend.repo.jpa.ToDoJpaRepository;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collection;
import java.util.Optional;

@Service
@Profile("jpa")
@Slf4j
public class ToDoServiceJpaImpl implements ToDoService {
    private final ToDoJpaRepository toDoJpaRepository;
    private final ToDoDetailJpaRepository toDoDetailJpaRepository;

    private CustomConfigLoader customConfigLoader;

    private Integer timeWindow;

    @Autowired
    public ToDoServiceJpaImpl(
            ToDoJpaRepository toDoJpaRepository, ToDoDetailJpaRepository toDoDetailJpaRepository,
            CustomConfigLoader customConfigLoader) {
        this.toDoJpaRepository = toDoJpaRepository;
        this.toDoDetailJpaRepository= toDoDetailJpaRepository;
        this.customConfigLoader = customConfigLoader;
        this.timeWindow = customConfigLoader.getTimeWindow();
        if (timeWindow == null) {
            this.timeWindow = 0;
        }
        log.info("The time window is {}", timeWindow);
    }

    @Override
    public void createToDo(ToDo toDo) throws IllegalArgumentException {
        validateToDo(toDo);
        toDo.setCreationDate(Instant.now());
        toDoJpaRepository.saveAndFlush(toDo);
    }

    @Override
    public void updateToDo(ToDo toDo) throws EntityNotFoundException, IllegalArgumentException {
        validateId(toDo.getId());
        validateToDo(toDo);
        toDoJpaRepository.update(toDo);
    }

    @Override
    public void deleteToDo(Long id) throws EntityNotFoundException {
        validateId(id);
        toDoJpaRepository.deleteById(id);
    }

    @Override
    public ToDo getById(Long id) throws EntityNotFoundException {
        Optional<ToDo> toDo = toDoJpaRepository.findById(id);
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
        return toDoJpaRepository.findByCreationDateAfter(Instant.now().minusSeconds(timeWindow));
    }

    @Override
    public Collection<ToDo> findByImportance(Integer levelOfImportance) {
        return toDoJpaRepository.findByLevelOfImportance(levelOfImportance);
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
        toDoDetailJpaRepository.saveAndFlush(toDoDetail);
        toDoJpaRepository.update(toDo);
    }

    @Override
    public void deleteDetailById(Long toDoId, Long toDoDetailId) throws EntityNotFoundException {
        ToDo toDo = getById(toDoId);
        Optional<ToDoDetail> toDoDetail = toDoDetailJpaRepository.findById(toDoDetailId);
        if (toDoDetail.isEmpty()) {
            throw new EntityNotFoundException("ToDoDetail with id " + toDoDetailId + " not found");
        } else {
            toDo.getDetails().remove(toDoDetail.get());
            toDoJpaRepository.update(toDo);
            toDoDetailJpaRepository.deleteById(toDoDetailId);
        }
    }
}
