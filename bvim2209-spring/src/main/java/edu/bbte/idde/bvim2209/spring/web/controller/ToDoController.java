package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.services.ToDoService;
import edu.bbte.idde.bvim2209.spring.web.dto.request.ToDoRequestDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.DeleteResponseDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.ToDoResponseDTO;
import edu.bbte.idde.bvim2209.spring.web.mapper.ToDoMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin("http://localhost:5173")
public class ToDoController {
    ToDoMapper toDoMapper;
    ToDoService toDoService;

    @Autowired
    public ToDoController(ToDoMapper toDoMapper, ToDoService toDoService) {
        this.toDoMapper = toDoMapper;
        this.toDoService = toDoService;
    }

    @GetMapping()
    public Collection<ToDoResponseDTO> getTodos(
            @RequestParam(value = "levelOfImportance", required = false) Integer levelOfImportance
    ) {
        if (levelOfImportance == null) {
            return toDoMapper.modelsToResponseDTO(toDoService.findAll());
        } else {
            return toDoMapper.modelsToResponseDTO(toDoService.findByImportance(levelOfImportance));
        }
    }

    @GetMapping("/{toDoId}")
    public ToDoResponseDTO getTodo(@PathVariable("toDoId") Long id) {
        ToDo toDo = toDoService.getById(id);
        return toDoMapper.modelToResponseDTO(toDo);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ToDoResponseDTO> createToDo(
            @Valid @RequestBody ToDoRequestDTO toDoDto) throws ParseException {
        ToDo toDo = toDoMapper.requestDTOToModel(toDoDto);
        toDoService.createToDo(toDo);
        URI createURI = URI.create("api/todos/" + toDo.getId());
        return ResponseEntity.created(createURI).body(toDoMapper.modelToResponseDTO(toDo));
    }

    @PutMapping("/{toDoId}")
    public ResponseEntity<ToDoResponseDTO> updateToDo(
            @PathVariable("toDoId") Long id,
            @Valid @RequestBody ToDoRequestDTO toDoDto) throws ParseException {
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

    @DeleteMapping()
    public DeleteResponseDTO deleteTodosBetweenDates(
            @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Optional<Date> afterDate,
            @RequestParam() @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            Optional<Date> beforeDate
            ) {
        return new DeleteResponseDTO(toDoService.deleteTodosBetweenDates(beforeDate, afterDate));
    }
}
