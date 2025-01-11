package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.spring.exceptions.InvalidJwtException;
import edu.bbte.idde.bvim2209.spring.exceptions.UnauthorizedException;

import java.text.ParseException;
import java.util.Collection;

public interface ToDoService {
    void createToDo(ToDo toDo, String jwtToken) throws
            ParseException, IllegalArgumentException, InvalidJwtException;

    void updateToDo(ToDo toDo, String jwtToken) throws
            EntityNotFoundException, ParseException, InvalidJwtException, UnauthorizedException;

    void deleteToDo(Long id, String jwtToken) throws
            EntityNotFoundException, ParseException, InvalidJwtException, UnauthorizedException;

    ToDo getById(Long id) throws EntityNotFoundException;

    Collection<ToDo> findAll();

    Collection<ToDo> findByImportance(Integer importanceLevel);

    Collection<ToDoDetail> getDetails(Long id);

    void addDetailToToDo(Long id, ToDoDetail toDoDetail) throws EntityNotFoundException;

    void deleteDetailById(Long toDoId, Long toDoDetailId) throws EntityNotFoundException;
}
