package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.backend.services.ToDoService;
import edu.bbte.idde.bvim2209.spring.web.dto.response.ToDoDetailResponseDTO;
import edu.bbte.idde.bvim2209.spring.web.mapper.ToDoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/todos")
public class ToDoDetailController {
    ToDoService toDoService;
    ToDoMapper toDoMapper;

    @Autowired
    public ToDoDetailController(ToDoMapper toDoMapper, ToDoService toDoService) {
        this.toDoMapper = toDoMapper;
        this.toDoService = toDoService;
    }

    @GetMapping("/{id}/details")
    public Collection<ToDoDetailResponseDTO> getToDetails(@PathVariable Long id) {
        ToDo toDo = toDoService.getById(id);
        Collection<ToDoDetail> details = toDo.getDetails();
        return toDoMapper.detailsToResponseDTOs(details);
    }
}
