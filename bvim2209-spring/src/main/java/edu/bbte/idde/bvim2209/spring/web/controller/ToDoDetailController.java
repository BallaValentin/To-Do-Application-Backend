package edu.bbte.idde.bvim2209.spring.web.controller;

import edu.bbte.idde.bvim2209.spring.backend.model.ToDoDetail;
import edu.bbte.idde.bvim2209.spring.backend.services.ToDoService;
import edu.bbte.idde.bvim2209.spring.web.dto.request.ToDoDetailRequestDTO;
import edu.bbte.idde.bvim2209.spring.web.dto.response.ToDoDetailResponseDTO;
import edu.bbte.idde.bvim2209.spring.web.mapper.ToDoDetailMapper;
import edu.bbte.idde.bvim2209.spring.web.mapper.ToDoMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin("http://localhost:5173")
public class ToDoDetailController {
    ToDoService toDoService;
    ToDoMapper toDoMapper;
    ToDoDetailMapper toDoDetailMapper;

    @Autowired
    public ToDoDetailController(
            ToDoMapper toDoMapper, ToDoDetailMapper toDoDetailMapper, ToDoService toDoService) {
        this.toDoMapper = toDoMapper;
        this.toDoDetailMapper = toDoDetailMapper;
        this.toDoService = toDoService;
    }

    @GetMapping("/{id}/details")
    public Collection<ToDoDetailResponseDTO> getToDetails(@PathVariable Long id) {
        return toDoMapper.detailsToResponseDTOs(toDoService.getDetails(id));
    }

    @PostMapping("/{id}/details")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ToDoDetailResponseDTO> createToDoDetailDTO(
            @PathVariable Long id, @Valid @RequestBody ToDoDetailRequestDTO toDoDetailRequestDTO,
            @RequestHeader("Authorization") String authorizationHeader) {
        ToDoDetail toDoDetail = toDoDetailMapper.requestDTOToModel(toDoDetailRequestDTO);
        String jwtToken = authorizationHeader.substring(7);
        toDoService.addDetailToToDo(id, toDoDetail, jwtToken);
        URI createURI = URI.create("api/todos/" + id + "/details/" + toDoDetail.getId());
        return ResponseEntity.created(createURI).body(
                toDoDetailMapper.modelToResponseDTO(toDoDetail)
        );
    }

    @DeleteMapping("/{todo-id}/details/{todo-detail-id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteToDoDetail(
            @PathVariable("todo-id") Long toDoId, @PathVariable("todo-detail-id") Long toDoDetailId,
            @RequestHeader("Authorization") String authorizationHeader) {
        String jwtToken = authorizationHeader.substring(7);
        toDoService.deleteDetailById(toDoId, toDoDetailId, jwtToken);
    }
}
