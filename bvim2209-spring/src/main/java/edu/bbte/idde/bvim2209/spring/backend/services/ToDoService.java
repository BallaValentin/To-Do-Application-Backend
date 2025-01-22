package edu.bbte.idde.bvim2209.spring.backend.services;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.bvim2209.spring.exceptions.AuthenticationException;
import edu.bbte.idde.bvim2209.spring.exceptions.UnauthorizedException;
import org.springframework.data.jpa.domain.Specification;

import java.text.ParseException;
import java.util.Collection;

public interface ToDoService {
    void createToDo(ToDo toDo, String jwtToken) throws
            ParseException, IllegalArgumentException, AuthenticationException;

    void updateToDo(ToDo toDo, String jwtToken) throws
            EntityNotFoundException, ParseException, AuthenticationException, UnauthorizedException;

    void deleteToDo(Long id, String jwtToken) throws
            EntityNotFoundException, ParseException, AuthenticationException, UnauthorizedException;

    ToDo getById(Long id) throws EntityNotFoundException;

    Collection<ToDo> findAll(Specification<ToDo> toDoSpecification);

    Collection<ToDo> findByImportance(Integer importanceLevel);

    Collection<ToDoDetail> getDetails(Long id);

    void addDetailToToDo(Long id, ToDoDetail toDoDetail, String jwtToken) throws EntityNotFoundException;

    void deleteDetailById(Long toDoId, Long toDoDetailId, String jwtToken) throws EntityNotFoundException;
}
