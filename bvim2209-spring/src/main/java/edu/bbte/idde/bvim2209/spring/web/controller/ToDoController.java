package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDo;
import edu.bbte.idde.bvim2209.spring.backend.services.ToDoService;
import edu.bbte.idde.bvim2209.spring.backend.specification.ToDoSpecification;
import edu.bbte.idde.bvim2209.spring.web.dto.request.ToDoRequestDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.ToDoResponseDTO;
import edu.bbte.idde.bvim2209.spring.web.mapper.ToDoMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;

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
            @RequestParam(required = false) Integer levelOfImportance,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date beforeDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date afterDate
    ) {
        Specification<ToDo> toDoSpecification = Specification.where(
                ToDoSpecification.withPriority(levelOfImportance))
                .and(ToDoSpecification.withDueDateBefore(beforeDate))
                .and(ToDoSpecification.withDueDateAfter(afterDate));

        return toDoMapper.modelsToResponseDTO(toDoService.findAll(toDoSpecification));
    }

    @GetMapping("/{toDoId}")
    public ToDoResponseDTO getTodo(@PathVariable("toDoId") Long id) {
        ToDo toDo = toDoService.getById(id);
        ToDoResponseDTO toDoResponseDTO = toDoMapper.modelToResponseDTO(toDo);
        toDoResponseDTO.setCreatedBy(toDo.getUser().getUsername());
        return toDoResponseDTO;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ToDoResponseDTO> createToDo(
            @Valid @RequestBody ToDoRequestDTO toDoDto,
            @RequestHeader("Authorization") String authorizationHeader) throws ParseException {
        ToDo toDo = toDoMapper.requestDTOToModel(toDoDto);
        String jwtToken = authorizationHeader.substring(7);
        toDoService.createToDo(toDo, jwtToken);
        URI createURI = URI.create("api/todos/" + toDo.getId());
        return ResponseEntity.created(createURI).body(toDoMapper.modelToResponseDTO(toDo));
    }

    @PutMapping("/{toDoId}")
    public ResponseEntity<ToDoResponseDTO> updateToDo(
            @PathVariable("toDoId") Long id,
            @Valid @RequestBody ToDoRequestDTO toDoDto,
            @RequestHeader("Authorization") String authorizationHeader) throws ParseException {
        ToDo toDoUpdates = toDoMapper.requestDTOToModel(toDoDto);
        toDoUpdates.setId(id);
        String jwtToken = authorizationHeader.substring(7);
        toDoService.updateToDo(toDoUpdates, jwtToken);
        return ResponseEntity.ok().body(toDoMapper.modelToResponseDTO(toDoUpdates));
    }

    @DeleteMapping("/{toDoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteToDo(
            @PathVariable("toDoId") Long id,
            @RequestHeader("Authorization") String authorizationHeader) throws ParseException {
        String jwtToken = authorizationHeader.substring(7);
        toDoService.deleteToDo(id, jwtToken);
    }
}
