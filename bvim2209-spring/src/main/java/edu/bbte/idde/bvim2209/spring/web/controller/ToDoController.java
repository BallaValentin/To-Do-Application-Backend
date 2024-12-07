package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.services.ToDoService;
import edu.bbte.idde.bvim2209.spring.web.dto.ToDoDto;
import edu.bbte.idde.bvim2209.spring.web.mapper.ToDoMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.ParseException;
import java.util.Collection;

@RestController
@RequestMapping("/api/todos")
public class ToDoController {
    ToDoMapper toDoMapper;
    ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoMapper toDoMapper, ToDoService toDoService) {
        this.toDoMapper = toDoMapper;
        this.toDoService = toDoService;
    }

    @GetMapping()
    public Collection<ToDoDto> findAll() {
        Collection<ToDo> todos = toDoService.findAll();
        return toDoMapper.modelsToDto(todos);
    }

    @GetMapping("/{toDoId}")
    public ToDoDto findById(@PathVariable("toDoId") Long id) {
        ToDo toDo = toDoService.findById(id);
        return toDoMapper.modelToDto(toDo);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ToDo> createToDo(@Valid @RequestBody ToDoDto toDoDto) throws ParseException {
        ToDo toDo = toDoMapper.dtoToModel(toDoDto);
        toDoService.createToDo(toDo);
        URI createURI = URI.create("api/todos/" + toDo.getId());
        return ResponseEntity.created(createURI).body(toDo);
    }

    @PutMapping("/{toDoId}")
    public String updateToDo(@PathVariable("toDoId") Long id, @Valid @RequestBody ToDoDto toDoDto) throws ParseException {
        ToDo toDo = toDoMapper.dtoToModel(toDoDto);
        toDo.setId(id);
        toDoService.updateToDo(toDo);
        return "ToDo with id: " + id + " updated successfully";
    }

    @DeleteMapping("/{toDoId}")
    public String deleteToDo(@PathVariable("toDoId") Long id) throws ParseException {
        toDoService.deleteToDo(id);
        return "ToDo with id: " + id + " deleted successfully";
    }
}
