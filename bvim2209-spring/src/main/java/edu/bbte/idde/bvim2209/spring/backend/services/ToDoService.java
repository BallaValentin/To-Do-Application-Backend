package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.util.Collection;

public interface ToDoService {
    void createToDo(ToDo toDo) throws ParseException, IllegalArgumentException;

    void updateToDo(ToDo toDo) throws EntityNotFoundException, ParseException;

    void deleteToDo(Long id) throws EntityNotFoundException, ParseException;

    ToDo getById(Long id) throws EntityNotFoundException;

    Collection<ToDo> findAll(Specification<ToDo> toDoSpecification);

    Collection<ToDo> findByImportance(Integer importanceLevel);

    Collection<ToDoDetail> getDetails(Long id);

    void addDetailToToDo(Long id, ToDoDetail toDoDetail) throws EntityNotFoundException;

    void deleteDetailById(Long toDoId, Long toDoDetailId) throws EntityNotFoundException;
}
