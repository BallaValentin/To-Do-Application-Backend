package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Page;

import java.text.ParseException;

public interface ToDoService {
    void createToDo(ToDo toDo) throws ParseException, IllegalArgumentException;

    void updateToDo(ToDo toDo) throws EntityNotFoundException, ParseException;

    void deleteToDo(Long id) throws EntityNotFoundException, ParseException;

    ToDo getById(Long id) throws EntityNotFoundException;

    Page<ToDo> findAll(Integer page, Integer size, String sortBy, String order);

    Page<ToDo> findByImportance(Integer importanceLevel, Integer page, Integer size, String sortBy, String order);

    Page<ToDoDetail> getDetails(Long id, Integer page, Integer size, String sortBy, String order);

    void addDetailToToDo(Long id, ToDoDetail toDoDetail) throws EntityNotFoundException;

    void deleteDetailById(Long toDoId, Long toDoDetailId) throws EntityNotFoundException;
}
