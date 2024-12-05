package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.services.ToDoService;
import edu.bbte.idde.bvim2209.spring.web.dto.ToDoDto;
import edu.bbte.idde.bvim2209.spring.web.mapper.ToDoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {
    ToDoMapper toDoMapper;
    ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoMapper toDoMapper, ToDoService toDoService)
    {
        this.toDoMapper = toDoMapper;
        this.toDoService = toDoService;
    }

    @GetMapping()
    public Collection<ToDoDto> findAll()
    {
        Collection<ToDo> todos = toDoService.findAll();
        return toDoMapper.modelsToDto(todos);
    }

    @GetMapping("/{toDoId}")
    public ToDoDto findById(@PathVariable("toDoId") Long id)
    {
        ToDo toDo = toDoService.findById(id);
        return  toDoMapper.modelToDto(toDo);
    }
}
