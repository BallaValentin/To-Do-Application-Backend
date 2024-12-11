package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.services.ToDoService;
import edu.bbte.idde.bvim2209.spring.web.dto.ToDoRequestDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.ToDoResponseDTO;
import edu.bbte.idde.bvim2209.spring.web.mapper.ToDoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    public Collection<ToDoResponseDTO> findAll() {
        Collection<ToDo> todos = toDoService.findAll();
        return toDoMapper.modelsToResponseDTO(todos);
    }

    @GetMapping("/{toDoId}")
    public ToDoResponseDTO findById(@PathVariable("toDoId") Long id) {
        ToDo toDo = toDoService.findById(id);
        return toDoMapper.modelToResponseDTO(toDo);
    }

    @GetMapping("/search")
    public Collection<ToDoResponseDTO> findByImportance(
            @RequestParam(value = "levelOfImportance", required = false) Integer levelOfImportance) {
        Collection<ToDo> todos = toDoService.findByImportance(levelOfImportance);
        return toDoMapper.modelsToResponseDTO(todos);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ToDoResponseDTO> createToDo(
            @Validated @RequestBody ToDoRequestDTO toDoDto) throws ParseException {
        ToDo toDo = toDoMapper.requestDTOToModel(toDoDto);
        toDoService.createToDo(toDo);
        URI createURI = URI.create("api/todos/" + toDo.getId());
        return ResponseEntity.created(createURI).body(toDoMapper.modelToResponseDTO(toDo));
    }

    @PutMapping("/{toDoId}")
    public ResponseEntity<ToDoResponseDTO> updateToDo(
            @PathVariable("toDoId") Long id,
            @Validated @RequestBody ToDoRequestDTO toDoDto) throws ParseException {
        ToDo toDo = toDoMapper.requestDTOToModel(toDoDto);
        toDo.setId(id);
        toDoService.updateToDo(toDo);
        return ResponseEntity.ok().body(toDoMapper.modelToResponseDTO(toDo));
    }

    @DeleteMapping("/{toDoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteToDo(@PathVariable("toDoId") Long id) throws ParseException {
        toDoService.deleteToDo(id);
    }
}
